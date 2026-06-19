package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.InscripcionAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Inscripcion;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.domain.enumeration.EstadoSolicitud;
import com.mycompany.playmatch.repository.InscripcionRepository;
import com.mycompany.playmatch.service.InscripcionService;
import com.mycompany.playmatch.service.dto.InscripcionDTO;
import com.mycompany.playmatch.service.mapper.InscripcionMapper;
import java.time.Instant;
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
 * Integration tests for the {@link InscripcionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InscripcionResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INSCRIPCION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INSCRIPCION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoSolicitud DEFAULT_ESTADO = EstadoSolicitud.PENDIENTE;
    private static final EstadoSolicitud UPDATED_ESTADO = EstadoSolicitud.APROBADA;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inscripcions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Mock
    private InscripcionRepository inscripcionRepositoryMock;

    @Autowired
    private InscripcionMapper inscripcionMapper;

    @Mock
    private InscripcionService inscripcionServiceMock;

    @Autowired
    private MockMvc restInscripcionMockMvc;

    private Inscripcion inscripcion;

    private Inscripcion insertedInscripcion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscripcion createEntity() {
        Inscripcion inscripcion = new Inscripcion()
            .codigo(DEFAULT_CODIGO)
            .fechaInscripcion(DEFAULT_FECHA_INSCRIPCION)
            .estado(DEFAULT_ESTADO)
            .observaciones(DEFAULT_OBSERVACIONES);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createEntity();
        torneo.setId("fixed-id-for-tests");
        inscripcion.setTorneo(torneo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createEntity();
        cuenta.setId("fixed-id-for-tests");
        inscripcion.setUsuario(cuenta);
        return inscripcion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscripcion createUpdatedEntity() {
        Inscripcion updatedInscripcion = new Inscripcion()
            .codigo(UPDATED_CODIGO)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION)
            .estado(UPDATED_ESTADO)
            .observaciones(UPDATED_OBSERVACIONES);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createUpdatedEntity();
        torneo.setId("fixed-id-for-tests");
        updatedInscripcion.setTorneo(torneo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createUpdatedEntity();
        cuenta.setId("fixed-id-for-tests");
        updatedInscripcion.setUsuario(cuenta);
        return updatedInscripcion;
    }

    @BeforeEach
    void initTest() {
        inscripcion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInscripcion != null) {
            inscripcionRepository.delete(insertedInscripcion);
            insertedInscripcion = null;
        }
    }

    @Test
    void createInscripcion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);
        var returnedInscripcionDTO = om.readValue(
            restInscripcionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscripcionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InscripcionDTO.class
        );

        // Validate the Inscripcion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInscripcion = inscripcionMapper.toEntity(returnedInscripcionDTO);
        assertInscripcionUpdatableFieldsEquals(returnedInscripcion, getPersistedInscripcion(returnedInscripcion));

        insertedInscripcion = returnedInscripcion;
    }

    @Test
    void createInscripcionWithExistingId() throws Exception {
        // Create the Inscripcion with an existing ID
        inscripcion.setId("existing_id");
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscripcionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inscripcion.setCodigo(null);

        // Create the Inscripcion, which fails.
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        restInscripcionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscripcionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaInscripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inscripcion.setFechaInscripcion(null);

        // Create the Inscripcion, which fails.
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        restInscripcionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscripcionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inscripcion.setEstado(null);

        // Create the Inscripcion, which fails.
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        restInscripcionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscripcionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllInscripcions() throws Exception {
        // Initialize the database
        insertedInscripcion = inscripcionRepository.save(inscripcion);

        // Get all the inscripcionList
        restInscripcionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscripcion.getId())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].fechaInscripcion").value(hasItem(DEFAULT_FECHA_INSCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInscripcionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(inscripcionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInscripcionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inscripcionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInscripcionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inscripcionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInscripcionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(inscripcionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getInscripcion() throws Exception {
        // Initialize the database
        insertedInscripcion = inscripcionRepository.save(inscripcion);

        // Get the inscripcion
        restInscripcionMockMvc
            .perform(get(ENTITY_API_URL_ID, inscripcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscripcion.getId()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.fechaInscripcion").value(DEFAULT_FECHA_INSCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }

    @Test
    void getNonExistingInscripcion() throws Exception {
        // Get the inscripcion
        restInscripcionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingInscripcion() throws Exception {
        // Initialize the database
        insertedInscripcion = inscripcionRepository.save(inscripcion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inscripcion
        Inscripcion updatedInscripcion = inscripcionRepository.findById(inscripcion.getId()).orElseThrow();
        updatedInscripcion
            .codigo(UPDATED_CODIGO)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION)
            .estado(UPDATED_ESTADO)
            .observaciones(UPDATED_OBSERVACIONES);
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(updatedInscripcion);

        restInscripcionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscripcionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inscripcionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInscripcionToMatchAllProperties(updatedInscripcion);
    }

    @Test
    void putNonExistingInscripcion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscripcion.setId(UUID.randomUUID().toString());

        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscripcionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscripcionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inscripcionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInscripcion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscripcion.setId(UUID.randomUUID().toString());

        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inscripcionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInscripcion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscripcion.setId(UUID.randomUUID().toString());

        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscripcionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInscripcionWithPatch() throws Exception {
        // Initialize the database
        insertedInscripcion = inscripcionRepository.save(inscripcion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inscripcion using partial update
        Inscripcion partialUpdatedInscripcion = new Inscripcion();
        partialUpdatedInscripcion.setId(inscripcion.getId());

        partialUpdatedInscripcion.estado(UPDATED_ESTADO).observaciones(UPDATED_OBSERVACIONES);

        restInscripcionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscripcion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInscripcion))
            )
            .andExpect(status().isOk());

        // Validate the Inscripcion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInscripcionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInscripcion, inscripcion),
            getPersistedInscripcion(inscripcion)
        );
    }

    @Test
    void fullUpdateInscripcionWithPatch() throws Exception {
        // Initialize the database
        insertedInscripcion = inscripcionRepository.save(inscripcion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inscripcion using partial update
        Inscripcion partialUpdatedInscripcion = new Inscripcion();
        partialUpdatedInscripcion.setId(inscripcion.getId());

        partialUpdatedInscripcion
            .codigo(UPDATED_CODIGO)
            .fechaInscripcion(UPDATED_FECHA_INSCRIPCION)
            .estado(UPDATED_ESTADO)
            .observaciones(UPDATED_OBSERVACIONES);

        restInscripcionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscripcion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInscripcion))
            )
            .andExpect(status().isOk());

        // Validate the Inscripcion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInscripcionUpdatableFieldsEquals(partialUpdatedInscripcion, getPersistedInscripcion(partialUpdatedInscripcion));
    }

    @Test
    void patchNonExistingInscripcion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscripcion.setId(UUID.randomUUID().toString());

        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscripcionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inscripcionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inscripcionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInscripcion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscripcion.setId(UUID.randomUUID().toString());

        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inscripcionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInscripcion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscripcion.setId(UUID.randomUUID().toString());

        // Create the Inscripcion
        InscripcionDTO inscripcionDTO = inscripcionMapper.toDto(inscripcion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inscripcionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscripcion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInscripcion() throws Exception {
        // Initialize the database
        insertedInscripcion = inscripcionRepository.save(inscripcion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inscripcion
        restInscripcionMockMvc
            .perform(delete(ENTITY_API_URL_ID, inscripcion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inscripcionRepository.count();
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

    protected Inscripcion getPersistedInscripcion(Inscripcion inscripcion) {
        return inscripcionRepository.findById(inscripcion.getId()).orElseThrow();
    }

    protected void assertPersistedInscripcionToMatchAllProperties(Inscripcion expectedInscripcion) {
        assertInscripcionAllPropertiesEquals(expectedInscripcion, getPersistedInscripcion(expectedInscripcion));
    }

    protected void assertPersistedInscripcionToMatchUpdatableProperties(Inscripcion expectedInscripcion) {
        assertInscripcionAllUpdatablePropertiesEquals(expectedInscripcion, getPersistedInscripcion(expectedInscripcion));
    }
}
