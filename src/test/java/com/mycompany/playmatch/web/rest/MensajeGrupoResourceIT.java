package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.MensajeGrupoAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.MensajeGrupo;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.repository.MensajeGrupoRepository;
import com.mycompany.playmatch.service.MensajeGrupoService;
import com.mycompany.playmatch.service.dto.MensajeGrupoDTO;
import com.mycompany.playmatch.service.mapper.MensajeGrupoMapper;
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
 * Integration tests for the {@link MensajeGrupoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MensajeGrupoResourceIT {

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_PUBLICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PUBLICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/mensaje-grupos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MensajeGrupoRepository mensajeGrupoRepository;

    @Mock
    private MensajeGrupoRepository mensajeGrupoRepositoryMock;

    @Autowired
    private MensajeGrupoMapper mensajeGrupoMapper;

    @Mock
    private MensajeGrupoService mensajeGrupoServiceMock;

    @Autowired
    private MockMvc restMensajeGrupoMockMvc;

    private MensajeGrupo mensajeGrupo;

    private MensajeGrupo insertedMensajeGrupo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MensajeGrupo createEntity() {
        MensajeGrupo mensajeGrupo = new MensajeGrupo()
            .contenido(DEFAULT_CONTENIDO)
            .fechaPublicacion(DEFAULT_FECHA_PUBLICACION)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Grupo grupo;
        grupo = GrupoResourceIT.createEntity();
        grupo.setId("fixed-id-for-tests");
        mensajeGrupo.setGrupo(grupo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createEntity();
        cuenta.setId("fixed-id-for-tests");
        mensajeGrupo.setAutor(cuenta);
        return mensajeGrupo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MensajeGrupo createUpdatedEntity() {
        MensajeGrupo updatedMensajeGrupo = new MensajeGrupo()
            .contenido(UPDATED_CONTENIDO)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Grupo grupo;
        grupo = GrupoResourceIT.createUpdatedEntity();
        grupo.setId("fixed-id-for-tests");
        updatedMensajeGrupo.setGrupo(grupo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createUpdatedEntity();
        cuenta.setId("fixed-id-for-tests");
        updatedMensajeGrupo.setAutor(cuenta);
        return updatedMensajeGrupo;
    }

    @BeforeEach
    void initTest() {
        mensajeGrupo = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMensajeGrupo != null) {
            mensajeGrupoRepository.delete(insertedMensajeGrupo);
            insertedMensajeGrupo = null;
        }
    }

    @Test
    void createMensajeGrupo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);
        var returnedMensajeGrupoDTO = om.readValue(
            restMensajeGrupoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeGrupoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MensajeGrupoDTO.class
        );

        // Validate the MensajeGrupo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMensajeGrupo = mensajeGrupoMapper.toEntity(returnedMensajeGrupoDTO);
        assertMensajeGrupoUpdatableFieldsEquals(returnedMensajeGrupo, getPersistedMensajeGrupo(returnedMensajeGrupo));

        insertedMensajeGrupo = returnedMensajeGrupo;
    }

    @Test
    void createMensajeGrupoWithExistingId() throws Exception {
        // Create the MensajeGrupo with an existing ID
        mensajeGrupo.setId("existing_id");
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeGrupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaPublicacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensajeGrupo.setFechaPublicacion(null);

        // Create the MensajeGrupo, which fails.
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        restMensajeGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeGrupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensajeGrupo.setEstado(null);

        // Create the MensajeGrupo, which fails.
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        restMensajeGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeGrupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllMensajeGrupos() throws Exception {
        // Initialize the database
        insertedMensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);

        // Get all the mensajeGrupoList
        restMensajeGrupoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensajeGrupo.getId())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO)))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMensajeGruposWithEagerRelationshipsIsEnabled() throws Exception {
        when(mensajeGrupoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMensajeGrupoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mensajeGrupoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMensajeGruposWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mensajeGrupoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMensajeGrupoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mensajeGrupoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getMensajeGrupo() throws Exception {
        // Initialize the database
        insertedMensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);

        // Get the mensajeGrupo
        restMensajeGrupoMockMvc
            .perform(get(ENTITY_API_URL_ID, mensajeGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensajeGrupo.getId()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingMensajeGrupo() throws Exception {
        // Get the mensajeGrupo
        restMensajeGrupoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMensajeGrupo() throws Exception {
        // Initialize the database
        insertedMensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensajeGrupo
        MensajeGrupo updatedMensajeGrupo = mensajeGrupoRepository.findById(mensajeGrupo.getId()).orElseThrow();
        updatedMensajeGrupo.contenido(UPDATED_CONTENIDO).fechaPublicacion(UPDATED_FECHA_PUBLICACION).estado(UPDATED_ESTADO);
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(updatedMensajeGrupo);

        restMensajeGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensajeGrupoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensajeGrupoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMensajeGrupoToMatchAllProperties(updatedMensajeGrupo);
    }

    @Test
    void putNonExistingMensajeGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensajeGrupo.setId(UUID.randomUUID().toString());

        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensajeGrupoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensajeGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMensajeGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensajeGrupo.setId(UUID.randomUUID().toString());

        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensajeGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMensajeGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensajeGrupo.setId(UUID.randomUUID().toString());

        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeGrupoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeGrupoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMensajeGrupoWithPatch() throws Exception {
        // Initialize the database
        insertedMensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensajeGrupo using partial update
        MensajeGrupo partialUpdatedMensajeGrupo = new MensajeGrupo();
        partialUpdatedMensajeGrupo.setId(mensajeGrupo.getId());

        partialUpdatedMensajeGrupo.estado(UPDATED_ESTADO);

        restMensajeGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensajeGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMensajeGrupo))
            )
            .andExpect(status().isOk());

        // Validate the MensajeGrupo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMensajeGrupoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMensajeGrupo, mensajeGrupo),
            getPersistedMensajeGrupo(mensajeGrupo)
        );
    }

    @Test
    void fullUpdateMensajeGrupoWithPatch() throws Exception {
        // Initialize the database
        insertedMensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensajeGrupo using partial update
        MensajeGrupo partialUpdatedMensajeGrupo = new MensajeGrupo();
        partialUpdatedMensajeGrupo.setId(mensajeGrupo.getId());

        partialUpdatedMensajeGrupo.contenido(UPDATED_CONTENIDO).fechaPublicacion(UPDATED_FECHA_PUBLICACION).estado(UPDATED_ESTADO);

        restMensajeGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensajeGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMensajeGrupo))
            )
            .andExpect(status().isOk());

        // Validate the MensajeGrupo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMensajeGrupoUpdatableFieldsEquals(partialUpdatedMensajeGrupo, getPersistedMensajeGrupo(partialUpdatedMensajeGrupo));
    }

    @Test
    void patchNonExistingMensajeGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensajeGrupo.setId(UUID.randomUUID().toString());

        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mensajeGrupoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mensajeGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMensajeGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensajeGrupo.setId(UUID.randomUUID().toString());

        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mensajeGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMensajeGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensajeGrupo.setId(UUID.randomUUID().toString());

        // Create the MensajeGrupo
        MensajeGrupoDTO mensajeGrupoDTO = mensajeGrupoMapper.toDto(mensajeGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeGrupoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mensajeGrupoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MensajeGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMensajeGrupo() throws Exception {
        // Initialize the database
        insertedMensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mensajeGrupo
        restMensajeGrupoMockMvc
            .perform(delete(ENTITY_API_URL_ID, mensajeGrupo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mensajeGrupoRepository.count();
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

    protected MensajeGrupo getPersistedMensajeGrupo(MensajeGrupo mensajeGrupo) {
        return mensajeGrupoRepository.findById(mensajeGrupo.getId()).orElseThrow();
    }

    protected void assertPersistedMensajeGrupoToMatchAllProperties(MensajeGrupo expectedMensajeGrupo) {
        assertMensajeGrupoAllPropertiesEquals(expectedMensajeGrupo, getPersistedMensajeGrupo(expectedMensajeGrupo));
    }

    protected void assertPersistedMensajeGrupoToMatchUpdatableProperties(MensajeGrupo expectedMensajeGrupo) {
        assertMensajeGrupoAllUpdatablePropertiesEquals(expectedMensajeGrupo, getPersistedMensajeGrupo(expectedMensajeGrupo));
    }
}
