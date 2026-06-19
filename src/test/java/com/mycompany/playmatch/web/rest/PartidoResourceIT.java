package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.PartidoAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.playmatch.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Partido;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.domain.enumeration.EstadoPartido;
import com.mycompany.playmatch.repository.PartidoRepository;
import com.mycompany.playmatch.service.PartidoService;
import com.mycompany.playmatch.service.dto.PartidoDTO;
import com.mycompany.playmatch.service.mapper.PartidoMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
 * Integration tests for the {@link PartidoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PartidoResourceIT {

    private static final ZonedDateTime DEFAULT_FECHA_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LUGAR = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIEMPO_MINUTOS = 1;
    private static final Integer UPDATED_TIEMPO_MINUTOS = 2;

    private static final Integer DEFAULT_MARCADOR_LOCAL = 1;
    private static final Integer UPDATED_MARCADOR_LOCAL = 2;

    private static final Integer DEFAULT_MARCADOR_VISITANTE = 1;
    private static final Integer UPDATED_MARCADOR_VISITANTE = 2;

    private static final EstadoPartido DEFAULT_ESTADO = EstadoPartido.PROGRAMADO;
    private static final EstadoPartido UPDATED_ESTADO = EstadoPartido.EN_CURSO;

    private static final String ENTITY_API_URL = "/api/partidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartidoRepository partidoRepository;

    @Mock
    private PartidoRepository partidoRepositoryMock;

    @Autowired
    private PartidoMapper partidoMapper;

    @Mock
    private PartidoService partidoServiceMock;

    @Autowired
    private MockMvc restPartidoMockMvc;

    private Partido partido;

    private Partido insertedPartido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partido createEntity() {
        Partido partido = new Partido()
            .fechaHora(DEFAULT_FECHA_HORA)
            .lugar(DEFAULT_LUGAR)
            .tiempoMinutos(DEFAULT_TIEMPO_MINUTOS)
            .marcadorLocal(DEFAULT_MARCADOR_LOCAL)
            .marcadorVisitante(DEFAULT_MARCADOR_VISITANTE)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createEntity();
        torneo.setId("fixed-id-for-tests");
        partido.setTorneo(torneo);
        return partido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partido createUpdatedEntity() {
        Partido updatedPartido = new Partido()
            .fechaHora(UPDATED_FECHA_HORA)
            .lugar(UPDATED_LUGAR)
            .tiempoMinutos(UPDATED_TIEMPO_MINUTOS)
            .marcadorLocal(UPDATED_MARCADOR_LOCAL)
            .marcadorVisitante(UPDATED_MARCADOR_VISITANTE)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createUpdatedEntity();
        torneo.setId("fixed-id-for-tests");
        updatedPartido.setTorneo(torneo);
        return updatedPartido;
    }

    @BeforeEach
    void initTest() {
        partido = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPartido != null) {
            partidoRepository.delete(insertedPartido);
            insertedPartido = null;
        }
    }

    @Test
    void createPartido() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);
        var returnedPartidoDTO = om.readValue(
            restPartidoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartidoDTO.class
        );

        // Validate the Partido in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartido = partidoMapper.toEntity(returnedPartidoDTO);
        assertPartidoUpdatableFieldsEquals(returnedPartido, getPersistedPartido(returnedPartido));

        insertedPartido = returnedPartido;
    }

    @Test
    void createPartidoWithExistingId() throws Exception {
        // Create the Partido with an existing ID
        partido.setId("existing_id");
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaHoraIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partido.setFechaHora(null);

        // Create the Partido, which fails.
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkLugarIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partido.setLugar(null);

        // Create the Partido, which fails.
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTiempoMinutosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partido.setTiempoMinutos(null);

        // Create the Partido, which fails.
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partido.setEstado(null);

        // Create the Partido, which fails.
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllPartidos() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.save(partido);

        // Get all the partidoList
        restPartidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partido.getId())))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(sameInstant(DEFAULT_FECHA_HORA))))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR)))
            .andExpect(jsonPath("$.[*].tiempoMinutos").value(hasItem(DEFAULT_TIEMPO_MINUTOS)))
            .andExpect(jsonPath("$.[*].marcadorLocal").value(hasItem(DEFAULT_MARCADOR_LOCAL)))
            .andExpect(jsonPath("$.[*].marcadorVisitante").value(hasItem(DEFAULT_MARCADOR_VISITANTE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPartidosWithEagerRelationshipsIsEnabled() throws Exception {
        when(partidoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPartidoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(partidoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPartidosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(partidoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPartidoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(partidoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPartido() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.save(partido);

        // Get the partido
        restPartidoMockMvc
            .perform(get(ENTITY_API_URL_ID, partido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partido.getId()))
            .andExpect(jsonPath("$.fechaHora").value(sameInstant(DEFAULT_FECHA_HORA)))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR))
            .andExpect(jsonPath("$.tiempoMinutos").value(DEFAULT_TIEMPO_MINUTOS))
            .andExpect(jsonPath("$.marcadorLocal").value(DEFAULT_MARCADOR_LOCAL))
            .andExpect(jsonPath("$.marcadorVisitante").value(DEFAULT_MARCADOR_VISITANTE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingPartido() throws Exception {
        // Get the partido
        restPartidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPartido() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.save(partido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partido
        Partido updatedPartido = partidoRepository.findById(partido.getId()).orElseThrow();
        updatedPartido
            .fechaHora(UPDATED_FECHA_HORA)
            .lugar(UPDATED_LUGAR)
            .tiempoMinutos(UPDATED_TIEMPO_MINUTOS)
            .marcadorLocal(UPDATED_MARCADOR_LOCAL)
            .marcadorVisitante(UPDATED_MARCADOR_VISITANTE)
            .estado(UPDATED_ESTADO);
        PartidoDTO partidoDTO = partidoMapper.toDto(updatedPartido);

        restPartidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partidoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartidoToMatchAllProperties(updatedPartido);
    }

    @Test
    void putNonExistingPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(UUID.randomUUID().toString());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partidoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(UUID.randomUUID().toString());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(UUID.randomUUID().toString());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePartidoWithPatch() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.save(partido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partido using partial update
        Partido partialUpdatedPartido = new Partido();
        partialUpdatedPartido.setId(partido.getId());

        partialUpdatedPartido.lugar(UPDATED_LUGAR).tiempoMinutos(UPDATED_TIEMPO_MINUTOS).marcadorLocal(UPDATED_MARCADOR_LOCAL);

        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartido))
            )
            .andExpect(status().isOk());

        // Validate the Partido in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartidoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPartido, partido), getPersistedPartido(partido));
    }

    @Test
    void fullUpdatePartidoWithPatch() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.save(partido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partido using partial update
        Partido partialUpdatedPartido = new Partido();
        partialUpdatedPartido.setId(partido.getId());

        partialUpdatedPartido
            .fechaHora(UPDATED_FECHA_HORA)
            .lugar(UPDATED_LUGAR)
            .tiempoMinutos(UPDATED_TIEMPO_MINUTOS)
            .marcadorLocal(UPDATED_MARCADOR_LOCAL)
            .marcadorVisitante(UPDATED_MARCADOR_VISITANTE)
            .estado(UPDATED_ESTADO);

        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartido))
            )
            .andExpect(status().isOk());

        // Validate the Partido in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartidoUpdatableFieldsEquals(partialUpdatedPartido, getPersistedPartido(partialUpdatedPartido));
    }

    @Test
    void patchNonExistingPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(UUID.randomUUID().toString());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partidoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(UUID.randomUUID().toString());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(UUID.randomUUID().toString());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePartido() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.save(partido);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partido
        restPartidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, partido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partidoRepository.count();
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

    protected Partido getPersistedPartido(Partido partido) {
        return partidoRepository.findById(partido.getId()).orElseThrow();
    }

    protected void assertPersistedPartidoToMatchAllProperties(Partido expectedPartido) {
        assertPartidoAllPropertiesEquals(expectedPartido, getPersistedPartido(expectedPartido));
    }

    protected void assertPersistedPartidoToMatchUpdatableProperties(Partido expectedPartido) {
        assertPartidoAllUpdatablePropertiesEquals(expectedPartido, getPersistedPartido(expectedPartido));
    }
}
