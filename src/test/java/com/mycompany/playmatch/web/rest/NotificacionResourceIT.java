package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.NotificacionAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Notificacion;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.repository.NotificacionRepository;
import com.mycompany.playmatch.service.dto.NotificacionDTO;
import com.mycompany.playmatch.service.mapper.NotificacionMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link NotificacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificacionResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LEIDA = false;
    private static final Boolean UPDATED_LEIDA = true;

    private static final Instant DEFAULT_FECHA_ENVIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ENVIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/notificacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private NotificacionMapper notificacionMapper;

    @Autowired
    private MockMvc restNotificacionMockMvc;

    private Notificacion notificacion;

    private Notificacion insertedNotificacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacion createEntity() {
        Notificacion notificacion = new Notificacion()
            .titulo(DEFAULT_TITULO)
            .mensaje(DEFAULT_MENSAJE)
            .leida(DEFAULT_LEIDA)
            .fechaEnvio(DEFAULT_FECHA_ENVIO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createEntity();
        cuenta.setId("fixed-id-for-tests");
        notificacion.setDestinatario(cuenta);
        return notificacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificacion createUpdatedEntity() {
        Notificacion updatedNotificacion = new Notificacion()
            .titulo(UPDATED_TITULO)
            .mensaje(UPDATED_MENSAJE)
            .leida(UPDATED_LEIDA)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createUpdatedEntity();
        cuenta.setId("fixed-id-for-tests");
        updatedNotificacion.setDestinatario(cuenta);
        return updatedNotificacion;
    }

    @BeforeEach
    void initTest() {
        notificacion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedNotificacion != null) {
            notificacionRepository.delete(insertedNotificacion);
            insertedNotificacion = null;
        }
    }

    @Test
    void createNotificacion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);
        var returnedNotificacionDTO = om.readValue(
            restNotificacionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NotificacionDTO.class
        );

        // Validate the Notificacion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNotificacion = notificacionMapper.toEntity(returnedNotificacionDTO);
        assertNotificacionUpdatableFieldsEquals(returnedNotificacion, getPersistedNotificacion(returnedNotificacion));

        insertedNotificacion = returnedNotificacion;
    }

    @Test
    void createNotificacionWithExistingId() throws Exception {
        // Create the Notificacion with an existing ID
        notificacion.setId("existing_id");
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        notificacion.setTitulo(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        restNotificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkLeidaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        notificacion.setLeida(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        restNotificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaEnvioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        notificacion.setFechaEnvio(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        restNotificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        notificacion.setEstado(null);

        // Create the Notificacion, which fails.
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        restNotificacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllNotificacions() throws Exception {
        // Initialize the database
        insertedNotificacion = notificacionRepository.save(notificacion);

        // Get all the notificacionList
        restNotificacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacion.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].leida").value(hasItem(DEFAULT_LEIDA)))
            .andExpect(jsonPath("$.[*].fechaEnvio").value(hasItem(DEFAULT_FECHA_ENVIO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    void getNotificacion() throws Exception {
        // Initialize the database
        insertedNotificacion = notificacionRepository.save(notificacion);

        // Get the notificacion
        restNotificacionMockMvc
            .perform(get(ENTITY_API_URL_ID, notificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificacion.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE))
            .andExpect(jsonPath("$.leida").value(DEFAULT_LEIDA))
            .andExpect(jsonPath("$.fechaEnvio").value(DEFAULT_FECHA_ENVIO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingNotificacion() throws Exception {
        // Get the notificacion
        restNotificacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingNotificacion() throws Exception {
        // Initialize the database
        insertedNotificacion = notificacionRepository.save(notificacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notificacion
        Notificacion updatedNotificacion = notificacionRepository.findById(notificacion.getId()).orElseThrow();
        updatedNotificacion
            .titulo(UPDATED_TITULO)
            .mensaje(UPDATED_MENSAJE)
            .leida(UPDATED_LEIDA)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .estado(UPDATED_ESTADO);
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(updatedNotificacion);

        restNotificacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notificacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNotificacionToMatchAllProperties(updatedNotificacion);
    }

    @Test
    void putNonExistingNotificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notificacion.setId(UUID.randomUUID().toString());

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNotificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notificacion.setId(UUID.randomUUID().toString());

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNotificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notificacion.setId(UUID.randomUUID().toString());

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNotificacionWithPatch() throws Exception {
        // Initialize the database
        insertedNotificacion = notificacionRepository.save(notificacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notificacion using partial update
        Notificacion partialUpdatedNotificacion = new Notificacion();
        partialUpdatedNotificacion.setId(notificacion.getId());

        partialUpdatedNotificacion.fechaEnvio(UPDATED_FECHA_ENVIO).estado(UPDATED_ESTADO);

        restNotificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotificacion))
            )
            .andExpect(status().isOk());

        // Validate the Notificacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotificacionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNotificacion, notificacion),
            getPersistedNotificacion(notificacion)
        );
    }

    @Test
    void fullUpdateNotificacionWithPatch() throws Exception {
        // Initialize the database
        insertedNotificacion = notificacionRepository.save(notificacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notificacion using partial update
        Notificacion partialUpdatedNotificacion = new Notificacion();
        partialUpdatedNotificacion.setId(notificacion.getId());

        partialUpdatedNotificacion
            .titulo(UPDATED_TITULO)
            .mensaje(UPDATED_MENSAJE)
            .leida(UPDATED_LEIDA)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .estado(UPDATED_ESTADO);

        restNotificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotificacion))
            )
            .andExpect(status().isOk());

        // Validate the Notificacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotificacionUpdatableFieldsEquals(partialUpdatedNotificacion, getPersistedNotificacion(partialUpdatedNotificacion));
    }

    @Test
    void patchNonExistingNotificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notificacion.setId(UUID.randomUUID().toString());

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(notificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNotificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notificacion.setId(UUID.randomUUID().toString());

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(notificacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNotificacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notificacion.setId(UUID.randomUUID().toString());

        // Create the Notificacion
        NotificacionDTO notificacionDTO = notificacionMapper.toDto(notificacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(notificacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notificacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNotificacion() throws Exception {
        // Initialize the database
        insertedNotificacion = notificacionRepository.save(notificacion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the notificacion
        restNotificacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return notificacionRepository.count();
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

    protected Notificacion getPersistedNotificacion(Notificacion notificacion) {
        return notificacionRepository.findById(notificacion.getId()).orElseThrow();
    }

    protected void assertPersistedNotificacionToMatchAllProperties(Notificacion expectedNotificacion) {
        assertNotificacionAllPropertiesEquals(expectedNotificacion, getPersistedNotificacion(expectedNotificacion));
    }

    protected void assertPersistedNotificacionToMatchUpdatableProperties(Notificacion expectedNotificacion) {
        assertNotificacionAllUpdatablePropertiesEquals(expectedNotificacion, getPersistedNotificacion(expectedNotificacion));
    }
}
