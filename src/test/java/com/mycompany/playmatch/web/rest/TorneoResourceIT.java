package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.TorneoAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.playmatch.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Categoria;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.domain.enumeration.EstadoTorneo;
import com.mycompany.playmatch.repository.TorneoRepository;
import com.mycompany.playmatch.service.TorneoService;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import com.mycompany.playmatch.service.mapper.TorneoMapper;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link TorneoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TorneoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HORA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_REGLAMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REGLAMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REGLAMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REGLAMENTO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_CUPO_MAXIMO_EQUIPOS = 1;
    private static final Integer UPDATED_CUPO_MAXIMO_EQUIPOS = 2;

    private static final Integer DEFAULT_CUPO_MAXIMO_JUGADORES = 1;
    private static final Integer UPDATED_CUPO_MAXIMO_JUGADORES = 2;

    private static final EstadoTorneo DEFAULT_ESTADO = EstadoTorneo.ACTIVO;
    private static final EstadoTorneo UPDATED_ESTADO = EstadoTorneo.FINALIZADO;

    private static final String ENTITY_API_URL = "/api/torneos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TorneoRepository torneoRepository;

    @Mock
    private TorneoRepository torneoRepositoryMock;

    @Autowired
    private TorneoMapper torneoMapper;

    @Mock
    private TorneoService torneoServiceMock;

    @Autowired
    private MockMvc restTorneoMockMvc;

    private Torneo torneo;

    private Torneo insertedTorneo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Torneo createEntity() {
        Torneo torneo = new Torneo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .horaInicio(DEFAULT_HORA_INICIO)
            .ubicacion(DEFAULT_UBICACION)
            .reglamento(DEFAULT_REGLAMENTO)
            .reglamentoContentType(DEFAULT_REGLAMENTO_CONTENT_TYPE)
            .cupoMaximoEquipos(DEFAULT_CUPO_MAXIMO_EQUIPOS)
            .cupoMaximoJugadores(DEFAULT_CUPO_MAXIMO_JUGADORES)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Categoria categoria;
        categoria = CategoriaResourceIT.createEntity();
        categoria.setId("fixed-id-for-tests");
        torneo.setCategoria(categoria);
        return torneo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Torneo createUpdatedEntity() {
        Torneo updatedTorneo = new Torneo()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaInicio(UPDATED_HORA_INICIO)
            .ubicacion(UPDATED_UBICACION)
            .reglamento(UPDATED_REGLAMENTO)
            .reglamentoContentType(UPDATED_REGLAMENTO_CONTENT_TYPE)
            .cupoMaximoEquipos(UPDATED_CUPO_MAXIMO_EQUIPOS)
            .cupoMaximoJugadores(UPDATED_CUPO_MAXIMO_JUGADORES)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Categoria categoria;
        categoria = CategoriaResourceIT.createUpdatedEntity();
        categoria.setId("fixed-id-for-tests");
        updatedTorneo.setCategoria(categoria);
        return updatedTorneo;
    }

    @BeforeEach
    void initTest() {
        torneo = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTorneo != null) {
            torneoRepository.delete(insertedTorneo);
            insertedTorneo = null;
        }
    }

    @Test
    void createTorneo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);
        var returnedTorneoDTO = om.readValue(
            restTorneoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TorneoDTO.class
        );

        // Validate the Torneo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTorneo = torneoMapper.toEntity(returnedTorneoDTO);
        assertTorneoUpdatableFieldsEquals(returnedTorneo, getPersistedTorneo(returnedTorneo));

        insertedTorneo = returnedTorneo;
    }

    @Test
    void createTorneoWithExistingId() throws Exception {
        // Create the Torneo with an existing ID
        torneo.setId("existing_id");
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTorneoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneo.setNombre(null);

        // Create the Torneo, which fails.
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        restTorneoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneo.setFechaInicio(null);

        // Create the Torneo, which fails.
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        restTorneoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkUbicacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneo.setUbicacion(null);

        // Create the Torneo, which fails.
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        restTorneoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCupoMaximoEquiposIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneo.setCupoMaximoEquipos(null);

        // Create the Torneo, which fails.
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        restTorneoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneo.setEstado(null);

        // Create the Torneo, which fails.
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        restTorneoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTorneos() throws Exception {
        // Initialize the database
        insertedTorneo = torneoRepository.save(torneo);

        // Get all the torneoList
        restTorneoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(torneo.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(sameInstant(DEFAULT_HORA_INICIO))))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION)))
            .andExpect(jsonPath("$.[*].reglamentoContentType").value(hasItem(DEFAULT_REGLAMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reglamento").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_REGLAMENTO))))
            .andExpect(jsonPath("$.[*].cupoMaximoEquipos").value(hasItem(DEFAULT_CUPO_MAXIMO_EQUIPOS)))
            .andExpect(jsonPath("$.[*].cupoMaximoJugadores").value(hasItem(DEFAULT_CUPO_MAXIMO_JUGADORES)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTorneosWithEagerRelationshipsIsEnabled() throws Exception {
        when(torneoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTorneoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(torneoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTorneosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(torneoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTorneoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(torneoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getTorneo() throws Exception {
        // Initialize the database
        insertedTorneo = torneoRepository.save(torneo);

        // Get the torneo
        restTorneoMockMvc
            .perform(get(ENTITY_API_URL_ID, torneo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(torneo.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.horaInicio").value(sameInstant(DEFAULT_HORA_INICIO)))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION))
            .andExpect(jsonPath("$.reglamentoContentType").value(DEFAULT_REGLAMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.reglamento").value(Base64.getEncoder().encodeToString(DEFAULT_REGLAMENTO)))
            .andExpect(jsonPath("$.cupoMaximoEquipos").value(DEFAULT_CUPO_MAXIMO_EQUIPOS))
            .andExpect(jsonPath("$.cupoMaximoJugadores").value(DEFAULT_CUPO_MAXIMO_JUGADORES))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingTorneo() throws Exception {
        // Get the torneo
        restTorneoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTorneo() throws Exception {
        // Initialize the database
        insertedTorneo = torneoRepository.save(torneo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the torneo
        Torneo updatedTorneo = torneoRepository.findById(torneo.getId()).orElseThrow();
        updatedTorneo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaInicio(UPDATED_HORA_INICIO)
            .ubicacion(UPDATED_UBICACION)
            .reglamento(UPDATED_REGLAMENTO)
            .reglamentoContentType(UPDATED_REGLAMENTO_CONTENT_TYPE)
            .cupoMaximoEquipos(UPDATED_CUPO_MAXIMO_EQUIPOS)
            .cupoMaximoJugadores(UPDATED_CUPO_MAXIMO_JUGADORES)
            .estado(UPDATED_ESTADO);
        TorneoDTO torneoDTO = torneoMapper.toDto(updatedTorneo);

        restTorneoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, torneoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTorneoToMatchAllProperties(updatedTorneo);
    }

    @Test
    void putNonExistingTorneo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneo.setId(UUID.randomUUID().toString());

        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTorneoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, torneoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTorneo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneo.setId(UUID.randomUUID().toString());

        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(torneoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTorneo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneo.setId(UUID.randomUUID().toString());

        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTorneoWithPatch() throws Exception {
        // Initialize the database
        insertedTorneo = torneoRepository.save(torneo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the torneo using partial update
        Torneo partialUpdatedTorneo = new Torneo();
        partialUpdatedTorneo.setId(torneo.getId());

        partialUpdatedTorneo
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaInicio(UPDATED_HORA_INICIO)
            .reglamento(UPDATED_REGLAMENTO)
            .reglamentoContentType(UPDATED_REGLAMENTO_CONTENT_TYPE)
            .cupoMaximoEquipos(UPDATED_CUPO_MAXIMO_EQUIPOS)
            .cupoMaximoJugadores(UPDATED_CUPO_MAXIMO_JUGADORES)
            .estado(UPDATED_ESTADO);

        restTorneoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTorneo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTorneo))
            )
            .andExpect(status().isOk());

        // Validate the Torneo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTorneoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTorneo, torneo), getPersistedTorneo(torneo));
    }

    @Test
    void fullUpdateTorneoWithPatch() throws Exception {
        // Initialize the database
        insertedTorneo = torneoRepository.save(torneo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the torneo using partial update
        Torneo partialUpdatedTorneo = new Torneo();
        partialUpdatedTorneo.setId(torneo.getId());

        partialUpdatedTorneo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaInicio(UPDATED_HORA_INICIO)
            .ubicacion(UPDATED_UBICACION)
            .reglamento(UPDATED_REGLAMENTO)
            .reglamentoContentType(UPDATED_REGLAMENTO_CONTENT_TYPE)
            .cupoMaximoEquipos(UPDATED_CUPO_MAXIMO_EQUIPOS)
            .cupoMaximoJugadores(UPDATED_CUPO_MAXIMO_JUGADORES)
            .estado(UPDATED_ESTADO);

        restTorneoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTorneo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTorneo))
            )
            .andExpect(status().isOk());

        // Validate the Torneo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTorneoUpdatableFieldsEquals(partialUpdatedTorneo, getPersistedTorneo(partialUpdatedTorneo));
    }

    @Test
    void patchNonExistingTorneo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneo.setId(UUID.randomUUID().toString());

        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTorneoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, torneoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(torneoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTorneo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneo.setId(UUID.randomUUID().toString());

        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(torneoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTorneo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneo.setId(UUID.randomUUID().toString());

        // Create the Torneo
        TorneoDTO torneoDTO = torneoMapper.toDto(torneo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(torneoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Torneo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTorneo() throws Exception {
        // Initialize the database
        insertedTorneo = torneoRepository.save(torneo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the torneo
        restTorneoMockMvc
            .perform(delete(ENTITY_API_URL_ID, torneo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return torneoRepository.count();
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

    protected Torneo getPersistedTorneo(Torneo torneo) {
        return torneoRepository.findById(torneo.getId()).orElseThrow();
    }

    protected void assertPersistedTorneoToMatchAllProperties(Torneo expectedTorneo) {
        assertTorneoAllPropertiesEquals(expectedTorneo, getPersistedTorneo(expectedTorneo));
    }

    protected void assertPersistedTorneoToMatchUpdatableProperties(Torneo expectedTorneo) {
        assertTorneoAllUpdatablePropertiesEquals(expectedTorneo, getPersistedTorneo(expectedTorneo));
    }
}
