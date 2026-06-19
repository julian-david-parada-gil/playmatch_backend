package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.CalendarioEventoAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.playmatch.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.CalendarioEvento;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.TipoEventoCalendario;
import com.mycompany.playmatch.repository.CalendarioEventoRepository;
import com.mycompany.playmatch.service.CalendarioEventoService;
import com.mycompany.playmatch.service.dto.CalendarioEventoDTO;
import com.mycompany.playmatch.service.mapper.CalendarioEventoMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CalendarioEventoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CalendarioEventoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CALENDARIO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CALENDARIO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CALENDARIO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CALENDARIO_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_FECHA_EVENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_EVENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final TipoEventoCalendario DEFAULT_TIPO = TipoEventoCalendario.INSCRIPCION;
    private static final TipoEventoCalendario UPDATED_TIPO = TipoEventoCalendario.PARTIDO;

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/calendario-eventos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CalendarioEventoRepository calendarioEventoRepository;

    @Mock
    private CalendarioEventoRepository calendarioEventoRepositoryMock;

    @Autowired
    private CalendarioEventoMapper calendarioEventoMapper;

    @Mock
    private CalendarioEventoService calendarioEventoServiceMock;

    @Autowired
    private MockMvc restCalendarioEventoMockMvc;

    private CalendarioEvento calendarioEvento;

    private CalendarioEvento insertedCalendarioEvento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarioEvento createEntity() {
        CalendarioEvento calendarioEvento = new CalendarioEvento()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .calendario(DEFAULT_CALENDARIO)
            .calendarioContentType(DEFAULT_CALENDARIO_CONTENT_TYPE)
            .fechaEvento(DEFAULT_FECHA_EVENTO)
            .tipo(DEFAULT_TIPO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createEntity();
        torneo.setId("fixed-id-for-tests");
        calendarioEvento.setTorneo(torneo);
        return calendarioEvento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarioEvento createUpdatedEntity() {
        CalendarioEvento updatedCalendarioEvento = new CalendarioEvento()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .calendario(UPDATED_CALENDARIO)
            .calendarioContentType(UPDATED_CALENDARIO_CONTENT_TYPE)
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createUpdatedEntity();
        torneo.setId("fixed-id-for-tests");
        updatedCalendarioEvento.setTorneo(torneo);
        return updatedCalendarioEvento;
    }

    @BeforeEach
    void initTest() {
        calendarioEvento = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCalendarioEvento != null) {
            calendarioEventoRepository.delete(insertedCalendarioEvento);
            insertedCalendarioEvento = null;
        }
    }

    @Test
    void createCalendarioEvento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);
        var returnedCalendarioEventoDTO = om.readValue(
            restCalendarioEventoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CalendarioEventoDTO.class
        );

        // Validate the CalendarioEvento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCalendarioEvento = calendarioEventoMapper.toEntity(returnedCalendarioEventoDTO);
        assertCalendarioEventoUpdatableFieldsEquals(returnedCalendarioEvento, getPersistedCalendarioEvento(returnedCalendarioEvento));

        insertedCalendarioEvento = returnedCalendarioEvento;
    }

    @Test
    void createCalendarioEventoWithExistingId() throws Exception {
        // Create the CalendarioEvento with an existing ID
        calendarioEvento.setId("existing_id");
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarioEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calendarioEvento.setTitulo(null);

        // Create the CalendarioEvento, which fails.
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        restCalendarioEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaEventoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calendarioEvento.setFechaEvento(null);

        // Create the CalendarioEvento, which fails.
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        restCalendarioEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calendarioEvento.setTipo(null);

        // Create the CalendarioEvento, which fails.
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        restCalendarioEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calendarioEvento.setEstado(null);

        // Create the CalendarioEvento, which fails.
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        restCalendarioEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCalendarioEventos() throws Exception {
        // Initialize the database
        insertedCalendarioEvento = calendarioEventoRepository.save(calendarioEvento);

        // Get all the calendarioEventoList
        restCalendarioEventoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarioEvento.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].calendarioContentType").value(hasItem(DEFAULT_CALENDARIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].calendario").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_CALENDARIO))))
            .andExpect(jsonPath("$.[*].fechaEvento").value(hasItem(sameInstant(DEFAULT_FECHA_EVENTO))))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCalendarioEventosWithEagerRelationshipsIsEnabled() throws Exception {
        when(calendarioEventoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCalendarioEventoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(calendarioEventoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCalendarioEventosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(calendarioEventoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCalendarioEventoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(calendarioEventoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCalendarioEvento() throws Exception {
        // Initialize the database
        insertedCalendarioEvento = calendarioEventoRepository.save(calendarioEvento);

        // Get the calendarioEvento
        restCalendarioEventoMockMvc
            .perform(get(ENTITY_API_URL_ID, calendarioEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendarioEvento.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.calendarioContentType").value(DEFAULT_CALENDARIO_CONTENT_TYPE))
            .andExpect(jsonPath("$.calendario").value(Base64.getEncoder().encodeToString(DEFAULT_CALENDARIO)))
            .andExpect(jsonPath("$.fechaEvento").value(sameInstant(DEFAULT_FECHA_EVENTO)))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingCalendarioEvento() throws Exception {
        // Get the calendarioEvento
        restCalendarioEventoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCalendarioEvento() throws Exception {
        // Initialize the database
        insertedCalendarioEvento = calendarioEventoRepository.save(calendarioEvento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calendarioEvento
        CalendarioEvento updatedCalendarioEvento = calendarioEventoRepository.findById(calendarioEvento.getId()).orElseThrow();
        updatedCalendarioEvento
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .calendario(UPDATED_CALENDARIO)
            .calendarioContentType(UPDATED_CALENDARIO_CONTENT_TYPE)
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO);
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(updatedCalendarioEvento);

        restCalendarioEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendarioEventoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calendarioEventoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCalendarioEventoToMatchAllProperties(updatedCalendarioEvento);
    }

    @Test
    void putNonExistingCalendarioEvento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calendarioEvento.setId(UUID.randomUUID().toString());

        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarioEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendarioEventoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calendarioEventoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCalendarioEvento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calendarioEvento.setId(UUID.randomUUID().toString());

        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calendarioEventoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCalendarioEvento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calendarioEvento.setId(UUID.randomUUID().toString());

        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioEventoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCalendarioEventoWithPatch() throws Exception {
        // Initialize the database
        insertedCalendarioEvento = calendarioEventoRepository.save(calendarioEvento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calendarioEvento using partial update
        CalendarioEvento partialUpdatedCalendarioEvento = new CalendarioEvento();
        partialUpdatedCalendarioEvento.setId(calendarioEvento.getId());

        partialUpdatedCalendarioEvento
            .titulo(UPDATED_TITULO)
            .calendario(UPDATED_CALENDARIO)
            .calendarioContentType(UPDATED_CALENDARIO_CONTENT_TYPE)
            .tipo(UPDATED_TIPO);

        restCalendarioEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendarioEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalendarioEvento))
            )
            .andExpect(status().isOk());

        // Validate the CalendarioEvento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalendarioEventoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCalendarioEvento, calendarioEvento),
            getPersistedCalendarioEvento(calendarioEvento)
        );
    }

    @Test
    void fullUpdateCalendarioEventoWithPatch() throws Exception {
        // Initialize the database
        insertedCalendarioEvento = calendarioEventoRepository.save(calendarioEvento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calendarioEvento using partial update
        CalendarioEvento partialUpdatedCalendarioEvento = new CalendarioEvento();
        partialUpdatedCalendarioEvento.setId(calendarioEvento.getId());

        partialUpdatedCalendarioEvento
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .calendario(UPDATED_CALENDARIO)
            .calendarioContentType(UPDATED_CALENDARIO_CONTENT_TYPE)
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO);

        restCalendarioEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendarioEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalendarioEvento))
            )
            .andExpect(status().isOk());

        // Validate the CalendarioEvento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalendarioEventoUpdatableFieldsEquals(
            partialUpdatedCalendarioEvento,
            getPersistedCalendarioEvento(partialUpdatedCalendarioEvento)
        );
    }

    @Test
    void patchNonExistingCalendarioEvento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calendarioEvento.setId(UUID.randomUUID().toString());

        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarioEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calendarioEventoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calendarioEventoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCalendarioEvento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calendarioEvento.setId(UUID.randomUUID().toString());

        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calendarioEventoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCalendarioEvento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calendarioEvento.setId(UUID.randomUUID().toString());

        // Create the CalendarioEvento
        CalendarioEventoDTO calendarioEventoDTO = calendarioEventoMapper.toDto(calendarioEvento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioEventoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(calendarioEventoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalendarioEvento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCalendarioEvento() throws Exception {
        // Initialize the database
        insertedCalendarioEvento = calendarioEventoRepository.save(calendarioEvento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the calendarioEvento
        restCalendarioEventoMockMvc
            .perform(delete(ENTITY_API_URL_ID, calendarioEvento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return calendarioEventoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CalendarioEvento getPersistedCalendarioEvento(CalendarioEvento calendarioEvento) {
        return calendarioEventoRepository.findById(calendarioEvento.getId()).orElseThrow();
    }

    protected void assertPersistedCalendarioEventoToMatchAllProperties(CalendarioEvento expectedCalendarioEvento) {
        assertCalendarioEventoAllPropertiesEquals(expectedCalendarioEvento, getPersistedCalendarioEvento(expectedCalendarioEvento));
    }

    protected void assertPersistedCalendarioEventoToMatchUpdatableProperties(CalendarioEvento expectedCalendarioEvento) {
        assertCalendarioEventoAllUpdatablePropertiesEquals(
            expectedCalendarioEvento,
            getPersistedCalendarioEvento(expectedCalendarioEvento)
        );
    }
}
