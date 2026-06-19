package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.GrupoAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.repository.GrupoRepository;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.mapper.GrupoMapper;
import java.util.Base64;
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
 * Integration tests for the {@link GrupoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ESCUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ESCUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ESCUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ESCUDO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_LIMITE_PARTICIPANTES = 1;
    private static final Integer UPDATED_LIMITE_PARTICIPANTES = 2;

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/grupos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private MockMvc restGrupoMockMvc;

    private Grupo grupo;

    private Grupo insertedGrupo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grupo createEntity() {
        return new Grupo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .escudo(DEFAULT_ESCUDO)
            .escudoContentType(DEFAULT_ESCUDO_CONTENT_TYPE)
            .limiteParticipantes(DEFAULT_LIMITE_PARTICIPANTES)
            .estado(DEFAULT_ESTADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grupo createUpdatedEntity() {
        return new Grupo()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .escudo(UPDATED_ESCUDO)
            .escudoContentType(UPDATED_ESCUDO_CONTENT_TYPE)
            .limiteParticipantes(UPDATED_LIMITE_PARTICIPANTES)
            .estado(UPDATED_ESTADO);
    }

    @BeforeEach
    void initTest() {
        grupo = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedGrupo != null) {
            grupoRepository.delete(insertedGrupo);
            insertedGrupo = null;
        }
    }

    @Test
    void createGrupo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);
        var returnedGrupoDTO = om.readValue(
            restGrupoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoDTO.class
        );

        // Validate the Grupo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGrupo = grupoMapper.toEntity(returnedGrupoDTO);
        assertGrupoUpdatableFieldsEquals(returnedGrupo, getPersistedGrupo(returnedGrupo));

        insertedGrupo = returnedGrupo;
    }

    @Test
    void createGrupoWithExistingId() throws Exception {
        // Create the Grupo with an existing ID
        grupo.setId("existing_id");
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupo.setNombre(null);

        // Create the Grupo, which fails.
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkLimiteParticipantesIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupo.setLimiteParticipantes(null);

        // Create the Grupo, which fails.
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupo.setEstado(null);

        // Create the Grupo, which fails.
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllGrupos() throws Exception {
        // Initialize the database
        insertedGrupo = grupoRepository.save(grupo);

        // Get all the grupoList
        restGrupoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupo.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].escudoContentType").value(hasItem(DEFAULT_ESCUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].escudo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_ESCUDO))))
            .andExpect(jsonPath("$.[*].limiteParticipantes").value(hasItem(DEFAULT_LIMITE_PARTICIPANTES)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    void getGrupo() throws Exception {
        // Initialize the database
        insertedGrupo = grupoRepository.save(grupo);

        // Get the grupo
        restGrupoMockMvc
            .perform(get(ENTITY_API_URL_ID, grupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupo.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.escudoContentType").value(DEFAULT_ESCUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.escudo").value(Base64.getEncoder().encodeToString(DEFAULT_ESCUDO)))
            .andExpect(jsonPath("$.limiteParticipantes").value(DEFAULT_LIMITE_PARTICIPANTES))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingGrupo() throws Exception {
        // Get the grupo
        restGrupoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGrupo() throws Exception {
        // Initialize the database
        insertedGrupo = grupoRepository.save(grupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupo
        Grupo updatedGrupo = grupoRepository.findById(grupo.getId()).orElseThrow();
        updatedGrupo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .escudo(UPDATED_ESCUDO)
            .escudoContentType(UPDATED_ESCUDO_CONTENT_TYPE)
            .limiteParticipantes(UPDATED_LIMITE_PARTICIPANTES)
            .estado(UPDATED_ESTADO);
        GrupoDTO grupoDTO = grupoMapper.toDto(updatedGrupo);

        restGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoToMatchAllProperties(updatedGrupo);
    }

    @Test
    void putNonExistingGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupo.setId(UUID.randomUUID().toString());

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupo.setId(UUID.randomUUID().toString());

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupo.setId(UUID.randomUUID().toString());

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGrupoWithPatch() throws Exception {
        // Initialize the database
        insertedGrupo = grupoRepository.save(grupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupo using partial update
        Grupo partialUpdatedGrupo = new Grupo();
        partialUpdatedGrupo.setId(grupo.getId());

        partialUpdatedGrupo
            .descripcion(UPDATED_DESCRIPCION)
            .escudo(UPDATED_ESCUDO)
            .escudoContentType(UPDATED_ESCUDO_CONTENT_TYPE)
            .estado(UPDATED_ESTADO);

        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupo))
            )
            .andExpect(status().isOk());

        // Validate the Grupo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGrupo, grupo), getPersistedGrupo(grupo));
    }

    @Test
    void fullUpdateGrupoWithPatch() throws Exception {
        // Initialize the database
        insertedGrupo = grupoRepository.save(grupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupo using partial update
        Grupo partialUpdatedGrupo = new Grupo();
        partialUpdatedGrupo.setId(grupo.getId());

        partialUpdatedGrupo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .escudo(UPDATED_ESCUDO)
            .escudoContentType(UPDATED_ESCUDO_CONTENT_TYPE)
            .limiteParticipantes(UPDATED_LIMITE_PARTICIPANTES)
            .estado(UPDATED_ESTADO);

        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupo))
            )
            .andExpect(status().isOk());

        // Validate the Grupo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoUpdatableFieldsEquals(partialUpdatedGrupo, getPersistedGrupo(partialUpdatedGrupo));
    }

    @Test
    void patchNonExistingGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupo.setId(UUID.randomUUID().toString());

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupo.setId(UUID.randomUUID().toString());

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupo.setId(UUID.randomUUID().toString());

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGrupo() throws Exception {
        // Initialize the database
        insertedGrupo = grupoRepository.save(grupo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupo
        restGrupoMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoRepository.count();
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

    protected Grupo getPersistedGrupo(Grupo grupo) {
        return grupoRepository.findById(grupo.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoToMatchAllProperties(Grupo expectedGrupo) {
        assertGrupoAllPropertiesEquals(expectedGrupo, getPersistedGrupo(expectedGrupo));
    }

    protected void assertPersistedGrupoToMatchUpdatableProperties(Grupo expectedGrupo) {
        assertGrupoAllUpdatablePropertiesEquals(expectedGrupo, getPersistedGrupo(expectedGrupo));
    }
}
