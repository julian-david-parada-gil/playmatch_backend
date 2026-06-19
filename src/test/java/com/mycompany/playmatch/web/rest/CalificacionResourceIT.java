package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.CalificacionAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Calificacion;
import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.repository.CalificacionRepository;
import com.mycompany.playmatch.service.CalificacionService;
import com.mycompany.playmatch.service.dto.CalificacionDTO;
import com.mycompany.playmatch.service.mapper.CalificacionMapper;
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
 * Integration tests for the {@link CalificacionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CalificacionResourceIT {

    private static final Integer DEFAULT_PUNTAJE = 1;
    private static final Integer UPDATED_PUNTAJE = 2;

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CALIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CALIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/calificacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Mock
    private CalificacionRepository calificacionRepositoryMock;

    @Autowired
    private CalificacionMapper calificacionMapper;

    @Mock
    private CalificacionService calificacionServiceMock;

    @Autowired
    private MockMvc restCalificacionMockMvc;

    private Calificacion calificacion;

    private Calificacion insertedCalificacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calificacion createEntity() {
        Calificacion calificacion = new Calificacion()
            .puntaje(DEFAULT_PUNTAJE)
            .comentario(DEFAULT_COMENTARIO)
            .fechaCalificacion(DEFAULT_FECHA_CALIFICACION)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createEntity();
        torneo.setId("fixed-id-for-tests");
        calificacion.setTorneo(torneo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createEntity();
        cuenta.setId("fixed-id-for-tests");
        calificacion.setAutor(cuenta);
        return calificacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calificacion createUpdatedEntity() {
        Calificacion updatedCalificacion = new Calificacion()
            .puntaje(UPDATED_PUNTAJE)
            .comentario(UPDATED_COMENTARIO)
            .fechaCalificacion(UPDATED_FECHA_CALIFICACION)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createUpdatedEntity();
        torneo.setId("fixed-id-for-tests");
        updatedCalificacion.setTorneo(torneo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createUpdatedEntity();
        cuenta.setId("fixed-id-for-tests");
        updatedCalificacion.setAutor(cuenta);
        return updatedCalificacion;
    }

    @BeforeEach
    void initTest() {
        calificacion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCalificacion != null) {
            calificacionRepository.delete(insertedCalificacion);
            insertedCalificacion = null;
        }
    }

    @Test
    void createCalificacion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);
        var returnedCalificacionDTO = om.readValue(
            restCalificacionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CalificacionDTO.class
        );

        // Validate the Calificacion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCalificacion = calificacionMapper.toEntity(returnedCalificacionDTO);
        assertCalificacionUpdatableFieldsEquals(returnedCalificacion, getPersistedCalificacion(returnedCalificacion));

        insertedCalificacion = returnedCalificacion;
    }

    @Test
    void createCalificacionWithExistingId() throws Exception {
        // Create the Calificacion with an existing ID
        calificacion.setId("existing_id");
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkPuntajeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calificacion.setPuntaje(null);

        // Create the Calificacion, which fails.
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        restCalificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaCalificacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calificacion.setFechaCalificacion(null);

        // Create the Calificacion, which fails.
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        restCalificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        calificacion.setEstado(null);

        // Create the Calificacion, which fails.
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        restCalificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCalificacions() throws Exception {
        // Initialize the database
        insertedCalificacion = calificacionRepository.save(calificacion);

        // Get all the calificacionList
        restCalificacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calificacion.getId())))
            .andExpect(jsonPath("$.[*].puntaje").value(hasItem(DEFAULT_PUNTAJE)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fechaCalificacion").value(hasItem(DEFAULT_FECHA_CALIFICACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCalificacionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(calificacionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCalificacionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(calificacionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCalificacionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(calificacionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCalificacionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(calificacionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCalificacion() throws Exception {
        // Initialize the database
        insertedCalificacion = calificacionRepository.save(calificacion);

        // Get the calificacion
        restCalificacionMockMvc
            .perform(get(ENTITY_API_URL_ID, calificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calificacion.getId()))
            .andExpect(jsonPath("$.puntaje").value(DEFAULT_PUNTAJE))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.fechaCalificacion").value(DEFAULT_FECHA_CALIFICACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingCalificacion() throws Exception {
        // Get the calificacion
        restCalificacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCalificacion() throws Exception {
        // Initialize the database
        insertedCalificacion = calificacionRepository.save(calificacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calificacion
        Calificacion updatedCalificacion = calificacionRepository.findById(calificacion.getId()).orElseThrow();
        updatedCalificacion
            .puntaje(UPDATED_PUNTAJE)
            .comentario(UPDATED_COMENTARIO)
            .fechaCalificacion(UPDATED_FECHA_CALIFICACION)
            .estado(UPDATED_ESTADO);
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(updatedCalificacion);

        restCalificacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calificacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calificacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCalificacionToMatchAllProperties(updatedCalificacion);
    }

    @Test
    void putNonExistingCalificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacion.setId(UUID.randomUUID().toString());

        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalificacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calificacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCalificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacion.setId(UUID.randomUUID().toString());

        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCalificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacion.setId(UUID.randomUUID().toString());

        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCalificacionWithPatch() throws Exception {
        // Initialize the database
        insertedCalificacion = calificacionRepository.save(calificacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calificacion using partial update
        Calificacion partialUpdatedCalificacion = new Calificacion();
        partialUpdatedCalificacion.setId(calificacion.getId());

        partialUpdatedCalificacion.estado(UPDATED_ESTADO);

        restCalificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalificacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalificacion))
            )
            .andExpect(status().isOk());

        // Validate the Calificacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalificacionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCalificacion, calificacion),
            getPersistedCalificacion(calificacion)
        );
    }

    @Test
    void fullUpdateCalificacionWithPatch() throws Exception {
        // Initialize the database
        insertedCalificacion = calificacionRepository.save(calificacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calificacion using partial update
        Calificacion partialUpdatedCalificacion = new Calificacion();
        partialUpdatedCalificacion.setId(calificacion.getId());

        partialUpdatedCalificacion
            .puntaje(UPDATED_PUNTAJE)
            .comentario(UPDATED_COMENTARIO)
            .fechaCalificacion(UPDATED_FECHA_CALIFICACION)
            .estado(UPDATED_ESTADO);

        restCalificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalificacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalificacion))
            )
            .andExpect(status().isOk());

        // Validate the Calificacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalificacionUpdatableFieldsEquals(partialUpdatedCalificacion, getPersistedCalificacion(partialUpdatedCalificacion));
    }

    @Test
    void patchNonExistingCalificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacion.setId(UUID.randomUUID().toString());

        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calificacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCalificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacion.setId(UUID.randomUUID().toString());

        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCalificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacion.setId(UUID.randomUUID().toString());

        // Create the Calificacion
        CalificacionDTO calificacionDTO = calificacionMapper.toDto(calificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(calificacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCalificacion() throws Exception {
        // Initialize the database
        insertedCalificacion = calificacionRepository.save(calificacion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the calificacion
        restCalificacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, calificacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return calificacionRepository.count();
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

    protected Calificacion getPersistedCalificacion(Calificacion calificacion) {
        return calificacionRepository.findById(calificacion.getId()).orElseThrow();
    }

    protected void assertPersistedCalificacionToMatchAllProperties(Calificacion expectedCalificacion) {
        assertCalificacionAllPropertiesEquals(expectedCalificacion, getPersistedCalificacion(expectedCalificacion));
    }

    protected void assertPersistedCalificacionToMatchUpdatableProperties(Calificacion expectedCalificacion) {
        assertCalificacionAllUpdatablePropertiesEquals(expectedCalificacion, getPersistedCalificacion(expectedCalificacion));
    }
}
