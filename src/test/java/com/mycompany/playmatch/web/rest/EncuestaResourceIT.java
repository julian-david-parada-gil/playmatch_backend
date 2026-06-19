package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.EncuestaAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Encuesta;
import com.mycompany.playmatch.domain.enumeration.EstadoEncuesta;
import com.mycompany.playmatch.repository.EncuestaRepository;
import com.mycompany.playmatch.service.EncuestaService;
import com.mycompany.playmatch.service.dto.EncuestaDTO;
import com.mycompany.playmatch.service.mapper.EncuestaMapper;
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
 * Integration tests for the {@link EncuestaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EncuestaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_OPCION_1 = "AAAAAAAAAA";
    private static final String UPDATED_OPCION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_OPCION_2 = "AAAAAAAAAA";
    private static final String UPDATED_OPCION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_OPCION_3 = "AAAAAAAAAA";
    private static final String UPDATED_OPCION_3 = "BBBBBBBBBB";

    private static final String DEFAULT_OPCION_4 = "AAAAAAAAAA";
    private static final String UPDATED_OPCION_4 = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTOS_OPCION_1 = 1;
    private static final Integer UPDATED_VOTOS_OPCION_1 = 2;

    private static final Integer DEFAULT_VOTOS_OPCION_2 = 1;
    private static final Integer UPDATED_VOTOS_OPCION_2 = 2;

    private static final Integer DEFAULT_VOTOS_OPCION_3 = 1;
    private static final Integer UPDATED_VOTOS_OPCION_3 = 2;

    private static final Integer DEFAULT_VOTOS_OPCION_4 = 1;
    private static final Integer UPDATED_VOTOS_OPCION_4 = 2;

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoEncuesta DEFAULT_ESTADO = EstadoEncuesta.ACTIVA;
    private static final EstadoEncuesta UPDATED_ESTADO = EstadoEncuesta.INACTIVA;

    private static final String ENTITY_API_URL = "/api/encuestas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EncuestaRepository encuestaRepository;

    @Mock
    private EncuestaRepository encuestaRepositoryMock;

    @Autowired
    private EncuestaMapper encuestaMapper;

    @Mock
    private EncuestaService encuestaServiceMock;

    @Autowired
    private MockMvc restEncuestaMockMvc;

    private Encuesta encuesta;

    private Encuesta insertedEncuesta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encuesta createEntity() {
        return new Encuesta()
            .titulo(DEFAULT_TITULO)
            .opcion1(DEFAULT_OPCION_1)
            .opcion2(DEFAULT_OPCION_2)
            .opcion3(DEFAULT_OPCION_3)
            .opcion4(DEFAULT_OPCION_4)
            .votosOpcion1(DEFAULT_VOTOS_OPCION_1)
            .votosOpcion2(DEFAULT_VOTOS_OPCION_2)
            .votosOpcion3(DEFAULT_VOTOS_OPCION_3)
            .votosOpcion4(DEFAULT_VOTOS_OPCION_4)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .estado(DEFAULT_ESTADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encuesta createUpdatedEntity() {
        return new Encuesta()
            .titulo(UPDATED_TITULO)
            .opcion1(UPDATED_OPCION_1)
            .opcion2(UPDATED_OPCION_2)
            .opcion3(UPDATED_OPCION_3)
            .opcion4(UPDATED_OPCION_4)
            .votosOpcion1(UPDATED_VOTOS_OPCION_1)
            .votosOpcion2(UPDATED_VOTOS_OPCION_2)
            .votosOpcion3(UPDATED_VOTOS_OPCION_3)
            .votosOpcion4(UPDATED_VOTOS_OPCION_4)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .estado(UPDATED_ESTADO);
    }

    @BeforeEach
    void initTest() {
        encuesta = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEncuesta != null) {
            encuestaRepository.delete(insertedEncuesta);
            insertedEncuesta = null;
        }
    }

    @Test
    void createEncuesta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);
        var returnedEncuestaDTO = om.readValue(
            restEncuestaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(encuestaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EncuestaDTO.class
        );

        // Validate the Encuesta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEncuesta = encuestaMapper.toEntity(returnedEncuestaDTO);
        assertEncuestaUpdatableFieldsEquals(returnedEncuesta, getPersistedEncuesta(returnedEncuesta));

        insertedEncuesta = returnedEncuesta;
    }

    @Test
    void createEncuestaWithExistingId() throws Exception {
        // Create the Encuesta with an existing ID
        encuesta.setId("existing_id");
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(encuestaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkOpcion1IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        encuesta.setOpcion1(null);

        // Create the Encuesta, which fails.
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        restEncuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(encuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkOpcion2IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        encuesta.setOpcion2(null);

        // Create the Encuesta, which fails.
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        restEncuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(encuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        encuesta.setEstado(null);

        // Create the Encuesta, which fails.
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        restEncuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(encuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllEncuestas() throws Exception {
        // Initialize the database
        insertedEncuesta = encuestaRepository.save(encuesta);

        // Get all the encuestaList
        restEncuestaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuesta.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].opcion1").value(hasItem(DEFAULT_OPCION_1)))
            .andExpect(jsonPath("$.[*].opcion2").value(hasItem(DEFAULT_OPCION_2)))
            .andExpect(jsonPath("$.[*].opcion3").value(hasItem(DEFAULT_OPCION_3)))
            .andExpect(jsonPath("$.[*].opcion4").value(hasItem(DEFAULT_OPCION_4)))
            .andExpect(jsonPath("$.[*].votosOpcion1").value(hasItem(DEFAULT_VOTOS_OPCION_1)))
            .andExpect(jsonPath("$.[*].votosOpcion2").value(hasItem(DEFAULT_VOTOS_OPCION_2)))
            .andExpect(jsonPath("$.[*].votosOpcion3").value(hasItem(DEFAULT_VOTOS_OPCION_3)))
            .andExpect(jsonPath("$.[*].votosOpcion4").value(hasItem(DEFAULT_VOTOS_OPCION_4)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEncuestasWithEagerRelationshipsIsEnabled() throws Exception {
        when(encuestaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEncuestaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(encuestaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEncuestasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(encuestaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEncuestaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(encuestaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getEncuesta() throws Exception {
        // Initialize the database
        insertedEncuesta = encuestaRepository.save(encuesta);

        // Get the encuesta
        restEncuestaMockMvc
            .perform(get(ENTITY_API_URL_ID, encuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encuesta.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.opcion1").value(DEFAULT_OPCION_1))
            .andExpect(jsonPath("$.opcion2").value(DEFAULT_OPCION_2))
            .andExpect(jsonPath("$.opcion3").value(DEFAULT_OPCION_3))
            .andExpect(jsonPath("$.opcion4").value(DEFAULT_OPCION_4))
            .andExpect(jsonPath("$.votosOpcion1").value(DEFAULT_VOTOS_OPCION_1))
            .andExpect(jsonPath("$.votosOpcion2").value(DEFAULT_VOTOS_OPCION_2))
            .andExpect(jsonPath("$.votosOpcion3").value(DEFAULT_VOTOS_OPCION_3))
            .andExpect(jsonPath("$.votosOpcion4").value(DEFAULT_VOTOS_OPCION_4))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingEncuesta() throws Exception {
        // Get the encuesta
        restEncuestaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEncuesta() throws Exception {
        // Initialize the database
        insertedEncuesta = encuestaRepository.save(encuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the encuesta
        Encuesta updatedEncuesta = encuestaRepository.findById(encuesta.getId()).orElseThrow();
        updatedEncuesta
            .titulo(UPDATED_TITULO)
            .opcion1(UPDATED_OPCION_1)
            .opcion2(UPDATED_OPCION_2)
            .opcion3(UPDATED_OPCION_3)
            .opcion4(UPDATED_OPCION_4)
            .votosOpcion1(UPDATED_VOTOS_OPCION_1)
            .votosOpcion2(UPDATED_VOTOS_OPCION_2)
            .votosOpcion3(UPDATED_VOTOS_OPCION_3)
            .votosOpcion4(UPDATED_VOTOS_OPCION_4)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .estado(UPDATED_ESTADO);
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(updatedEncuesta);

        restEncuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encuestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(encuestaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEncuestaToMatchAllProperties(updatedEncuesta);
    }

    @Test
    void putNonExistingEncuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        encuesta.setId(UUID.randomUUID().toString());

        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encuestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(encuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEncuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        encuesta.setId(UUID.randomUUID().toString());

        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(encuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEncuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        encuesta.setId(UUID.randomUUID().toString());

        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncuestaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(encuestaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEncuestaWithPatch() throws Exception {
        // Initialize the database
        insertedEncuesta = encuestaRepository.save(encuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the encuesta using partial update
        Encuesta partialUpdatedEncuesta = new Encuesta();
        partialUpdatedEncuesta.setId(encuesta.getId());

        partialUpdatedEncuesta
            .opcion2(UPDATED_OPCION_2)
            .opcion3(UPDATED_OPCION_3)
            .votosOpcion2(UPDATED_VOTOS_OPCION_2)
            .votosOpcion4(UPDATED_VOTOS_OPCION_4)
            .fechaFin(UPDATED_FECHA_FIN);

        restEncuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncuesta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEncuesta))
            )
            .andExpect(status().isOk());

        // Validate the Encuesta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEncuestaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEncuesta, encuesta), getPersistedEncuesta(encuesta));
    }

    @Test
    void fullUpdateEncuestaWithPatch() throws Exception {
        // Initialize the database
        insertedEncuesta = encuestaRepository.save(encuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the encuesta using partial update
        Encuesta partialUpdatedEncuesta = new Encuesta();
        partialUpdatedEncuesta.setId(encuesta.getId());

        partialUpdatedEncuesta
            .titulo(UPDATED_TITULO)
            .opcion1(UPDATED_OPCION_1)
            .opcion2(UPDATED_OPCION_2)
            .opcion3(UPDATED_OPCION_3)
            .opcion4(UPDATED_OPCION_4)
            .votosOpcion1(UPDATED_VOTOS_OPCION_1)
            .votosOpcion2(UPDATED_VOTOS_OPCION_2)
            .votosOpcion3(UPDATED_VOTOS_OPCION_3)
            .votosOpcion4(UPDATED_VOTOS_OPCION_4)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .estado(UPDATED_ESTADO);

        restEncuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncuesta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEncuesta))
            )
            .andExpect(status().isOk());

        // Validate the Encuesta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEncuestaUpdatableFieldsEquals(partialUpdatedEncuesta, getPersistedEncuesta(partialUpdatedEncuesta));
    }

    @Test
    void patchNonExistingEncuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        encuesta.setId(UUID.randomUUID().toString());

        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, encuestaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(encuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEncuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        encuesta.setId(UUID.randomUUID().toString());

        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(encuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEncuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        encuesta.setId(UUID.randomUUID().toString());

        // Create the Encuesta
        EncuestaDTO encuestaDTO = encuestaMapper.toDto(encuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncuestaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(encuestaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEncuesta() throws Exception {
        // Initialize the database
        insertedEncuesta = encuestaRepository.save(encuesta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the encuesta
        restEncuestaMockMvc
            .perform(delete(ENTITY_API_URL_ID, encuesta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return encuestaRepository.count();
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

    protected Encuesta getPersistedEncuesta(Encuesta encuesta) {
        return encuestaRepository.findById(encuesta.getId()).orElseThrow();
    }

    protected void assertPersistedEncuestaToMatchAllProperties(Encuesta expectedEncuesta) {
        assertEncuestaAllPropertiesEquals(expectedEncuesta, getPersistedEncuesta(expectedEncuesta));
    }

    protected void assertPersistedEncuestaToMatchUpdatableProperties(Encuesta expectedEncuesta) {
        assertEncuestaAllUpdatablePropertiesEquals(expectedEncuesta, getPersistedEncuesta(expectedEncuesta));
    }
}
