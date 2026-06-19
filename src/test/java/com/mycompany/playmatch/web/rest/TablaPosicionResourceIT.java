package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.TablaPosicionAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.TablaPosicion;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.repository.TablaPosicionRepository;
import com.mycompany.playmatch.service.TablaPosicionService;
import com.mycompany.playmatch.service.dto.TablaPosicionDTO;
import com.mycompany.playmatch.service.mapper.TablaPosicionMapper;
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
 * Integration tests for the {@link TablaPosicionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TablaPosicionResourceIT {

    private static final Integer DEFAULT_PUNTOS = 1;
    private static final Integer UPDATED_PUNTOS = 2;

    private static final Integer DEFAULT_PARTIDOS_JUGADOS = 1;
    private static final Integer UPDATED_PARTIDOS_JUGADOS = 2;

    private static final Integer DEFAULT_PARTIDOS_GANADOS = 1;
    private static final Integer UPDATED_PARTIDOS_GANADOS = 2;

    private static final Integer DEFAULT_PARTIDOS_EMPATADOS = 1;
    private static final Integer UPDATED_PARTIDOS_EMPATADOS = 2;

    private static final Integer DEFAULT_PARTIDOS_PERDIDOS = 1;
    private static final Integer UPDATED_PARTIDOS_PERDIDOS = 2;

    private static final Integer DEFAULT_GOLES_FAVOR = 1;
    private static final Integer UPDATED_GOLES_FAVOR = 2;

    private static final Integer DEFAULT_GOLES_CONTRA = 1;
    private static final Integer UPDATED_GOLES_CONTRA = 2;

    private static final Integer DEFAULT_DIFERENCIA_GOLES = 1;
    private static final Integer UPDATED_DIFERENCIA_GOLES = 2;

    private static final String ENTITY_API_URL = "/api/tabla-posicions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TablaPosicionRepository tablaPosicionRepository;

    @Mock
    private TablaPosicionRepository tablaPosicionRepositoryMock;

    @Autowired
    private TablaPosicionMapper tablaPosicionMapper;

    @Mock
    private TablaPosicionService tablaPosicionServiceMock;

    @Autowired
    private MockMvc restTablaPosicionMockMvc;

    private TablaPosicion tablaPosicion;

    private TablaPosicion insertedTablaPosicion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TablaPosicion createEntity() {
        TablaPosicion tablaPosicion = new TablaPosicion()
            .puntos(DEFAULT_PUNTOS)
            .partidosJugados(DEFAULT_PARTIDOS_JUGADOS)
            .partidosGanados(DEFAULT_PARTIDOS_GANADOS)
            .partidosEmpatados(DEFAULT_PARTIDOS_EMPATADOS)
            .partidosPerdidos(DEFAULT_PARTIDOS_PERDIDOS)
            .golesFavor(DEFAULT_GOLES_FAVOR)
            .golesContra(DEFAULT_GOLES_CONTRA)
            .diferenciaGoles(DEFAULT_DIFERENCIA_GOLES);
        // Add required entity
        Grupo grupo;
        grupo = GrupoResourceIT.createEntity();
        grupo.setId("fixed-id-for-tests");
        tablaPosicion.setGrupo(grupo);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createEntity();
        torneo.setId("fixed-id-for-tests");
        tablaPosicion.setTorneo(torneo);
        return tablaPosicion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TablaPosicion createUpdatedEntity() {
        TablaPosicion updatedTablaPosicion = new TablaPosicion()
            .puntos(UPDATED_PUNTOS)
            .partidosJugados(UPDATED_PARTIDOS_JUGADOS)
            .partidosGanados(UPDATED_PARTIDOS_GANADOS)
            .partidosEmpatados(UPDATED_PARTIDOS_EMPATADOS)
            .partidosPerdidos(UPDATED_PARTIDOS_PERDIDOS)
            .golesFavor(UPDATED_GOLES_FAVOR)
            .golesContra(UPDATED_GOLES_CONTRA)
            .diferenciaGoles(UPDATED_DIFERENCIA_GOLES);
        // Add required entity
        Grupo grupo;
        grupo = GrupoResourceIT.createUpdatedEntity();
        grupo.setId("fixed-id-for-tests");
        updatedTablaPosicion.setGrupo(grupo);
        // Add required entity
        Torneo torneo;
        torneo = TorneoResourceIT.createUpdatedEntity();
        torneo.setId("fixed-id-for-tests");
        updatedTablaPosicion.setTorneo(torneo);
        return updatedTablaPosicion;
    }

    @BeforeEach
    void initTest() {
        tablaPosicion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTablaPosicion != null) {
            tablaPosicionRepository.delete(insertedTablaPosicion);
            insertedTablaPosicion = null;
        }
    }

    @Test
    void createTablaPosicion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);
        var returnedTablaPosicionDTO = om.readValue(
            restTablaPosicionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TablaPosicionDTO.class
        );

        // Validate the TablaPosicion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTablaPosicion = tablaPosicionMapper.toEntity(returnedTablaPosicionDTO);
        assertTablaPosicionUpdatableFieldsEquals(returnedTablaPosicion, getPersistedTablaPosicion(returnedTablaPosicion));

        insertedTablaPosicion = returnedTablaPosicion;
    }

    @Test
    void createTablaPosicionWithExistingId() throws Exception {
        // Create the TablaPosicion with an existing ID
        tablaPosicion.setId("existing_id");
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkPuntosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setPuntos(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPartidosJugadosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setPartidosJugados(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPartidosGanadosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setPartidosGanados(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPartidosEmpatadosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setPartidosEmpatados(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPartidosPerdidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setPartidosPerdidos(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkGolesFavorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setGolesFavor(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkGolesContraIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setGolesContra(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDiferenciaGolesIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tablaPosicion.setDiferenciaGoles(null);

        // Create the TablaPosicion, which fails.
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        restTablaPosicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTablaPosicions() throws Exception {
        // Initialize the database
        insertedTablaPosicion = tablaPosicionRepository.save(tablaPosicion);

        // Get all the tablaPosicionList
        restTablaPosicionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tablaPosicion.getId())))
            .andExpect(jsonPath("$.[*].puntos").value(hasItem(DEFAULT_PUNTOS)))
            .andExpect(jsonPath("$.[*].partidosJugados").value(hasItem(DEFAULT_PARTIDOS_JUGADOS)))
            .andExpect(jsonPath("$.[*].partidosGanados").value(hasItem(DEFAULT_PARTIDOS_GANADOS)))
            .andExpect(jsonPath("$.[*].partidosEmpatados").value(hasItem(DEFAULT_PARTIDOS_EMPATADOS)))
            .andExpect(jsonPath("$.[*].partidosPerdidos").value(hasItem(DEFAULT_PARTIDOS_PERDIDOS)))
            .andExpect(jsonPath("$.[*].golesFavor").value(hasItem(DEFAULT_GOLES_FAVOR)))
            .andExpect(jsonPath("$.[*].golesContra").value(hasItem(DEFAULT_GOLES_CONTRA)))
            .andExpect(jsonPath("$.[*].diferenciaGoles").value(hasItem(DEFAULT_DIFERENCIA_GOLES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTablaPosicionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tablaPosicionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTablaPosicionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tablaPosicionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTablaPosicionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tablaPosicionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTablaPosicionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tablaPosicionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getTablaPosicion() throws Exception {
        // Initialize the database
        insertedTablaPosicion = tablaPosicionRepository.save(tablaPosicion);

        // Get the tablaPosicion
        restTablaPosicionMockMvc
            .perform(get(ENTITY_API_URL_ID, tablaPosicion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tablaPosicion.getId()))
            .andExpect(jsonPath("$.puntos").value(DEFAULT_PUNTOS))
            .andExpect(jsonPath("$.partidosJugados").value(DEFAULT_PARTIDOS_JUGADOS))
            .andExpect(jsonPath("$.partidosGanados").value(DEFAULT_PARTIDOS_GANADOS))
            .andExpect(jsonPath("$.partidosEmpatados").value(DEFAULT_PARTIDOS_EMPATADOS))
            .andExpect(jsonPath("$.partidosPerdidos").value(DEFAULT_PARTIDOS_PERDIDOS))
            .andExpect(jsonPath("$.golesFavor").value(DEFAULT_GOLES_FAVOR))
            .andExpect(jsonPath("$.golesContra").value(DEFAULT_GOLES_CONTRA))
            .andExpect(jsonPath("$.diferenciaGoles").value(DEFAULT_DIFERENCIA_GOLES));
    }

    @Test
    void getNonExistingTablaPosicion() throws Exception {
        // Get the tablaPosicion
        restTablaPosicionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTablaPosicion() throws Exception {
        // Initialize the database
        insertedTablaPosicion = tablaPosicionRepository.save(tablaPosicion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tablaPosicion
        TablaPosicion updatedTablaPosicion = tablaPosicionRepository.findById(tablaPosicion.getId()).orElseThrow();
        updatedTablaPosicion
            .puntos(UPDATED_PUNTOS)
            .partidosJugados(UPDATED_PARTIDOS_JUGADOS)
            .partidosGanados(UPDATED_PARTIDOS_GANADOS)
            .partidosEmpatados(UPDATED_PARTIDOS_EMPATADOS)
            .partidosPerdidos(UPDATED_PARTIDOS_PERDIDOS)
            .golesFavor(UPDATED_GOLES_FAVOR)
            .golesContra(UPDATED_GOLES_CONTRA)
            .diferenciaGoles(UPDATED_DIFERENCIA_GOLES);
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(updatedTablaPosicion);

        restTablaPosicionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tablaPosicionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tablaPosicionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTablaPosicionToMatchAllProperties(updatedTablaPosicion);
    }

    @Test
    void putNonExistingTablaPosicion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tablaPosicion.setId(UUID.randomUUID().toString());

        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTablaPosicionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tablaPosicionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tablaPosicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTablaPosicion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tablaPosicion.setId(UUID.randomUUID().toString());

        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablaPosicionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tablaPosicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTablaPosicion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tablaPosicion.setId(UUID.randomUUID().toString());

        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablaPosicionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTablaPosicionWithPatch() throws Exception {
        // Initialize the database
        insertedTablaPosicion = tablaPosicionRepository.save(tablaPosicion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tablaPosicion using partial update
        TablaPosicion partialUpdatedTablaPosicion = new TablaPosicion();
        partialUpdatedTablaPosicion.setId(tablaPosicion.getId());

        partialUpdatedTablaPosicion
            .partidosEmpatados(UPDATED_PARTIDOS_EMPATADOS)
            .partidosPerdidos(UPDATED_PARTIDOS_PERDIDOS)
            .golesFavor(UPDATED_GOLES_FAVOR)
            .golesContra(UPDATED_GOLES_CONTRA)
            .diferenciaGoles(UPDATED_DIFERENCIA_GOLES);

        restTablaPosicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTablaPosicion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTablaPosicion))
            )
            .andExpect(status().isOk());

        // Validate the TablaPosicion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTablaPosicionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTablaPosicion, tablaPosicion),
            getPersistedTablaPosicion(tablaPosicion)
        );
    }

    @Test
    void fullUpdateTablaPosicionWithPatch() throws Exception {
        // Initialize the database
        insertedTablaPosicion = tablaPosicionRepository.save(tablaPosicion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tablaPosicion using partial update
        TablaPosicion partialUpdatedTablaPosicion = new TablaPosicion();
        partialUpdatedTablaPosicion.setId(tablaPosicion.getId());

        partialUpdatedTablaPosicion
            .puntos(UPDATED_PUNTOS)
            .partidosJugados(UPDATED_PARTIDOS_JUGADOS)
            .partidosGanados(UPDATED_PARTIDOS_GANADOS)
            .partidosEmpatados(UPDATED_PARTIDOS_EMPATADOS)
            .partidosPerdidos(UPDATED_PARTIDOS_PERDIDOS)
            .golesFavor(UPDATED_GOLES_FAVOR)
            .golesContra(UPDATED_GOLES_CONTRA)
            .diferenciaGoles(UPDATED_DIFERENCIA_GOLES);

        restTablaPosicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTablaPosicion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTablaPosicion))
            )
            .andExpect(status().isOk());

        // Validate the TablaPosicion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTablaPosicionUpdatableFieldsEquals(partialUpdatedTablaPosicion, getPersistedTablaPosicion(partialUpdatedTablaPosicion));
    }

    @Test
    void patchNonExistingTablaPosicion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tablaPosicion.setId(UUID.randomUUID().toString());

        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTablaPosicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tablaPosicionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tablaPosicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTablaPosicion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tablaPosicion.setId(UUID.randomUUID().toString());

        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablaPosicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tablaPosicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTablaPosicion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tablaPosicion.setId(UUID.randomUUID().toString());

        // Create the TablaPosicion
        TablaPosicionDTO tablaPosicionDTO = tablaPosicionMapper.toDto(tablaPosicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablaPosicionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tablaPosicionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TablaPosicion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTablaPosicion() throws Exception {
        // Initialize the database
        insertedTablaPosicion = tablaPosicionRepository.save(tablaPosicion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tablaPosicion
        restTablaPosicionMockMvc
            .perform(delete(ENTITY_API_URL_ID, tablaPosicion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tablaPosicionRepository.count();
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

    protected TablaPosicion getPersistedTablaPosicion(TablaPosicion tablaPosicion) {
        return tablaPosicionRepository.findById(tablaPosicion.getId()).orElseThrow();
    }

    protected void assertPersistedTablaPosicionToMatchAllProperties(TablaPosicion expectedTablaPosicion) {
        assertTablaPosicionAllPropertiesEquals(expectedTablaPosicion, getPersistedTablaPosicion(expectedTablaPosicion));
    }

    protected void assertPersistedTablaPosicionToMatchUpdatableProperties(TablaPosicion expectedTablaPosicion) {
        assertTablaPosicionAllUpdatablePropertiesEquals(expectedTablaPosicion, getPersistedTablaPosicion(expectedTablaPosicion));
    }
}
