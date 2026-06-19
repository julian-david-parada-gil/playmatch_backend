package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.ConvocatoriaAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Convocatoria;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.repository.ConvocatoriaRepository;
import com.mycompany.playmatch.service.ConvocatoriaService;
import com.mycompany.playmatch.service.dto.ConvocatoriaDTO;
import com.mycompany.playmatch.service.mapper.ConvocatoriaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ConvocatoriaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConvocatoriaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_PUBLICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PUBLICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_FECHA_INICIO_INSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO_INSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN_INSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN_INSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CUPOS = 1;
    private static final Integer UPDATED_CUPOS = 2;

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/convocatorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConvocatoriaRepository convocatoriaRepository;

    @Mock
    private ConvocatoriaRepository convocatoriaRepositoryMock;

    @Autowired
    private ConvocatoriaMapper convocatoriaMapper;

    @Mock
    private ConvocatoriaService convocatoriaServiceMock;

    @Autowired
    private MockMvc restConvocatoriaMockMvc;

    private Convocatoria convocatoria;

    private Convocatoria insertedConvocatoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convocatoria createEntity() {
        Convocatoria convocatoria = new Convocatoria()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaPublicacion(DEFAULT_FECHA_PUBLICACION)
            .fechaInicioInscripcion(DEFAULT_FECHA_INICIO_INSCRIPCION)
            .fechaFinInscripcion(DEFAULT_FECHA_FIN_INSCRIPCION)
            .cupos(DEFAULT_CUPOS)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createEntity();
        torneo.setId("fixed-id-for-tests");
        convocatoria.setTorneo(torneo);
        return convocatoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convocatoria createUpdatedEntity() {
        Convocatoria updatedConvocatoria = new Convocatoria()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .fechaInicioInscripcion(UPDATED_FECHA_INICIO_INSCRIPCION)
            .fechaFinInscripcion(UPDATED_FECHA_FIN_INSCRIPCION)
            .cupos(UPDATED_CUPOS)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createUpdatedEntity();
        torneo.setId("fixed-id-for-tests");
        updatedConvocatoria.setTorneo(torneo);
        return updatedConvocatoria;
    }

    @BeforeEach
    void initTest() {
        convocatoria = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedConvocatoria != null) {
            convocatoriaRepository.delete(insertedConvocatoria);
            insertedConvocatoria = null;
        }
    }

    @Test
    void createConvocatoria() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);
        var returnedConvocatoriaDTO = om.readValue(
            restConvocatoriaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(convocatoriaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConvocatoriaDTO.class
        );

        // Validate the Convocatoria in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConvocatoria = convocatoriaMapper.toEntity(returnedConvocatoriaDTO);
        assertConvocatoriaUpdatableFieldsEquals(returnedConvocatoria, getPersistedConvocatoria(returnedConvocatoria));

        insertedConvocatoria = returnedConvocatoria;
    }

    @Test
    void createConvocatoriaWithExistingId() throws Exception {
        // Create the Convocatoria with an existing ID
        convocatoria.setId("existing_id");
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConvocatoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(convocatoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        convocatoria.setTitulo(null);

        // Create the Convocatoria, which fails.
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        restConvocatoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(convocatoriaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaPublicacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        convocatoria.setFechaPublicacion(null);

        // Create the Convocatoria, which fails.
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        restConvocatoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(convocatoriaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        convocatoria.setEstado(null);

        // Create the Convocatoria, which fails.
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        restConvocatoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(convocatoriaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllConvocatorias() throws Exception {
        // Initialize the database
        insertedConvocatoria = convocatoriaRepository.save(convocatoria);

        // Get all the convocatoriaList
        restConvocatoriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convocatoria.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].fechaInicioInscripcion").value(hasItem(DEFAULT_FECHA_INICIO_INSCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaFinInscripcion").value(hasItem(DEFAULT_FECHA_FIN_INSCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].cupos").value(hasItem(DEFAULT_CUPOS)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConvocatoriasWithEagerRelationshipsIsEnabled() throws Exception {
        when(convocatoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConvocatoriaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(convocatoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConvocatoriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(convocatoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConvocatoriaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(convocatoriaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getConvocatoria() throws Exception {
        // Initialize the database
        insertedConvocatoria = convocatoriaRepository.save(convocatoria);

        // Get the convocatoria
        restConvocatoriaMockMvc
            .perform(get(ENTITY_API_URL_ID, convocatoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(convocatoria.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION.toString()))
            .andExpect(jsonPath("$.fechaInicioInscripcion").value(DEFAULT_FECHA_INICIO_INSCRIPCION.toString()))
            .andExpect(jsonPath("$.fechaFinInscripcion").value(DEFAULT_FECHA_FIN_INSCRIPCION.toString()))
            .andExpect(jsonPath("$.cupos").value(DEFAULT_CUPOS))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingConvocatoria() throws Exception {
        // Get the convocatoria
        restConvocatoriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingConvocatoria() throws Exception {
        // Initialize the database
        insertedConvocatoria = convocatoriaRepository.save(convocatoria);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the convocatoria
        Convocatoria updatedConvocatoria = convocatoriaRepository.findById(convocatoria.getId()).orElseThrow();
        updatedConvocatoria
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .fechaInicioInscripcion(UPDATED_FECHA_INICIO_INSCRIPCION)
            .fechaFinInscripcion(UPDATED_FECHA_FIN_INSCRIPCION)
            .cupos(UPDATED_CUPOS)
            .estado(UPDATED_ESTADO);
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(updatedConvocatoria);

        restConvocatoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, convocatoriaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(convocatoriaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConvocatoriaToMatchAllProperties(updatedConvocatoria);
    }

    @Test
    void putNonExistingConvocatoria() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConvocatoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, convocatoriaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(convocatoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConvocatoria() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConvocatoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(convocatoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConvocatoria() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConvocatoriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(convocatoriaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConvocatoriaWithPatch() throws Exception {
        // Initialize the database
        insertedConvocatoria = convocatoriaRepository.save(convocatoria);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the convocatoria using partial update
        Convocatoria partialUpdatedConvocatoria = new Convocatoria();
        partialUpdatedConvocatoria.setId(convocatoria.getId());

        partialUpdatedConvocatoria.titulo(UPDATED_TITULO).fechaPublicacion(UPDATED_FECHA_PUBLICACION).cupos(UPDATED_CUPOS);

        restConvocatoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConvocatoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConvocatoria))
            )
            .andExpect(status().isOk());

        // Validate the Convocatoria in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConvocatoriaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConvocatoria, convocatoria),
            getPersistedConvocatoria(convocatoria)
        );
    }

    @Test
    void fullUpdateConvocatoriaWithPatch() throws Exception {
        // Initialize the database
        insertedConvocatoria = convocatoriaRepository.save(convocatoria);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the convocatoria using partial update
        Convocatoria partialUpdatedConvocatoria = new Convocatoria();
        partialUpdatedConvocatoria.setId(convocatoria.getId());

        partialUpdatedConvocatoria
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .fechaInicioInscripcion(UPDATED_FECHA_INICIO_INSCRIPCION)
            .fechaFinInscripcion(UPDATED_FECHA_FIN_INSCRIPCION)
            .cupos(UPDATED_CUPOS)
            .estado(UPDATED_ESTADO);

        restConvocatoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConvocatoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConvocatoria))
            )
            .andExpect(status().isOk());

        // Validate the Convocatoria in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConvocatoriaUpdatableFieldsEquals(partialUpdatedConvocatoria, getPersistedConvocatoria(partialUpdatedConvocatoria));
    }

    @Test
    void patchNonExistingConvocatoria() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConvocatoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, convocatoriaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(convocatoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConvocatoria() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConvocatoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(convocatoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConvocatoria() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConvocatoriaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(convocatoriaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Convocatoria in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConvocatoria() throws Exception {
        // Initialize the database
        insertedConvocatoria = convocatoriaRepository.save(convocatoria);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the convocatoria
        restConvocatoriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, convocatoria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return convocatoriaRepository.count();
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

    protected Convocatoria getPersistedConvocatoria(Convocatoria convocatoria) {
        return convocatoriaRepository.findById(convocatoria.getId()).orElseThrow();
    }

    protected void assertPersistedConvocatoriaToMatchAllProperties(Convocatoria expectedConvocatoria) {
        assertConvocatoriaAllPropertiesEquals(expectedConvocatoria, getPersistedConvocatoria(expectedConvocatoria));
    }

    protected void assertPersistedConvocatoriaToMatchUpdatableProperties(Convocatoria expectedConvocatoria) {
        assertConvocatoriaAllUpdatablePropertiesEquals(expectedConvocatoria, getPersistedConvocatoria(expectedConvocatoria));
    }
}
