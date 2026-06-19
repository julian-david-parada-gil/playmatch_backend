package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.MiembroGrupoAsserts.*;
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
import com.mycompany.playmatch.domain.MiembroGrupo;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.repository.MiembroGrupoRepository;
import com.mycompany.playmatch.service.MiembroGrupoService;
import com.mycompany.playmatch.service.dto.MiembroGrupoDTO;
import com.mycompany.playmatch.service.mapper.MiembroGrupoMapper;
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
 * Integration tests for the {@link MiembroGrupoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MiembroGrupoResourceIT {

    private static final Instant DEFAULT_FECHA_INGRESO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INGRESO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final Boolean DEFAULT_ES_ADMINISTRADOR = false;
    private static final Boolean UPDATED_ES_ADMINISTRADOR = true;

    private static final String ENTITY_API_URL = "/api/miembro-grupos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MiembroGrupoRepository miembroGrupoRepository;

    @Mock
    private MiembroGrupoRepository miembroGrupoRepositoryMock;

    @Autowired
    private MiembroGrupoMapper miembroGrupoMapper;

    @Mock
    private MiembroGrupoService miembroGrupoServiceMock;

    @Autowired
    private MockMvc restMiembroGrupoMockMvc;

    private MiembroGrupo miembroGrupo;

    private MiembroGrupo insertedMiembroGrupo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiembroGrupo createEntity() {
        MiembroGrupo miembroGrupo = new MiembroGrupo()
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .estado(DEFAULT_ESTADO)
            .esAdministrador(DEFAULT_ES_ADMINISTRADOR);
        // Add required entity
        Grupo grupo;
        grupo = GrupoResourceIT.createEntity();
        grupo.setId("fixed-id-for-tests");
        miembroGrupo.setGrupo(grupo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createEntity();
        cuenta.setId("fixed-id-for-tests");
        miembroGrupo.setUsuario(cuenta);
        return miembroGrupo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiembroGrupo createUpdatedEntity() {
        MiembroGrupo updatedMiembroGrupo = new MiembroGrupo()
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .estado(UPDATED_ESTADO)
            .esAdministrador(UPDATED_ES_ADMINISTRADOR);
        // Add required entity
        Grupo grupo;
        grupo = GrupoResourceIT.createUpdatedEntity();
        grupo.setId("fixed-id-for-tests");
        updatedMiembroGrupo.setGrupo(grupo);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createUpdatedEntity();
        cuenta.setId("fixed-id-for-tests");
        updatedMiembroGrupo.setUsuario(cuenta);
        return updatedMiembroGrupo;
    }

    @BeforeEach
    void initTest() {
        miembroGrupo = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMiembroGrupo != null) {
            miembroGrupoRepository.delete(insertedMiembroGrupo);
            insertedMiembroGrupo = null;
        }
    }

    @Test
    void createMiembroGrupo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);
        var returnedMiembroGrupoDTO = om.readValue(
            restMiembroGrupoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miembroGrupoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MiembroGrupoDTO.class
        );

        // Validate the MiembroGrupo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMiembroGrupo = miembroGrupoMapper.toEntity(returnedMiembroGrupoDTO);
        assertMiembroGrupoUpdatableFieldsEquals(returnedMiembroGrupo, getPersistedMiembroGrupo(returnedMiembroGrupo));

        insertedMiembroGrupo = returnedMiembroGrupo;
    }

    @Test
    void createMiembroGrupoWithExistingId() throws Exception {
        // Create the MiembroGrupo with an existing ID
        miembroGrupo.setId("existing_id");
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMiembroGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miembroGrupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaIngresoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        miembroGrupo.setFechaIngreso(null);

        // Create the MiembroGrupo, which fails.
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        restMiembroGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miembroGrupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        miembroGrupo.setEstado(null);

        // Create the MiembroGrupo, which fails.
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        restMiembroGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miembroGrupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEsAdministradorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        miembroGrupo.setEsAdministrador(null);

        // Create the MiembroGrupo, which fails.
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        restMiembroGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miembroGrupoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllMiembroGrupos() throws Exception {
        // Initialize the database
        insertedMiembroGrupo = miembroGrupoRepository.save(miembroGrupo);

        // Get all the miembroGrupoList
        restMiembroGrupoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembroGrupo.getId())))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].esAdministrador").value(hasItem(DEFAULT_ES_ADMINISTRADOR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMiembroGruposWithEagerRelationshipsIsEnabled() throws Exception {
        when(miembroGrupoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMiembroGrupoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(miembroGrupoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMiembroGruposWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(miembroGrupoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMiembroGrupoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(miembroGrupoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getMiembroGrupo() throws Exception {
        // Initialize the database
        insertedMiembroGrupo = miembroGrupoRepository.save(miembroGrupo);

        // Get the miembroGrupo
        restMiembroGrupoMockMvc
            .perform(get(ENTITY_API_URL_ID, miembroGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(miembroGrupo.getId()))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.esAdministrador").value(DEFAULT_ES_ADMINISTRADOR));
    }

    @Test
    void getNonExistingMiembroGrupo() throws Exception {
        // Get the miembroGrupo
        restMiembroGrupoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMiembroGrupo() throws Exception {
        // Initialize the database
        insertedMiembroGrupo = miembroGrupoRepository.save(miembroGrupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the miembroGrupo
        MiembroGrupo updatedMiembroGrupo = miembroGrupoRepository.findById(miembroGrupo.getId()).orElseThrow();
        updatedMiembroGrupo.fechaIngreso(UPDATED_FECHA_INGRESO).estado(UPDATED_ESTADO).esAdministrador(UPDATED_ES_ADMINISTRADOR);
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(updatedMiembroGrupo);

        restMiembroGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, miembroGrupoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(miembroGrupoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMiembroGrupoToMatchAllProperties(updatedMiembroGrupo);
    }

    @Test
    void putNonExistingMiembroGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miembroGrupo.setId(UUID.randomUUID().toString());

        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiembroGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, miembroGrupoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(miembroGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMiembroGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miembroGrupo.setId(UUID.randomUUID().toString());

        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiembroGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(miembroGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMiembroGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miembroGrupo.setId(UUID.randomUUID().toString());

        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiembroGrupoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miembroGrupoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMiembroGrupoWithPatch() throws Exception {
        // Initialize the database
        insertedMiembroGrupo = miembroGrupoRepository.save(miembroGrupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the miembroGrupo using partial update
        MiembroGrupo partialUpdatedMiembroGrupo = new MiembroGrupo();
        partialUpdatedMiembroGrupo.setId(miembroGrupo.getId());

        partialUpdatedMiembroGrupo.fechaIngreso(UPDATED_FECHA_INGRESO);

        restMiembroGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMiembroGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMiembroGrupo))
            )
            .andExpect(status().isOk());

        // Validate the MiembroGrupo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMiembroGrupoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMiembroGrupo, miembroGrupo),
            getPersistedMiembroGrupo(miembroGrupo)
        );
    }

    @Test
    void fullUpdateMiembroGrupoWithPatch() throws Exception {
        // Initialize the database
        insertedMiembroGrupo = miembroGrupoRepository.save(miembroGrupo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the miembroGrupo using partial update
        MiembroGrupo partialUpdatedMiembroGrupo = new MiembroGrupo();
        partialUpdatedMiembroGrupo.setId(miembroGrupo.getId());

        partialUpdatedMiembroGrupo.fechaIngreso(UPDATED_FECHA_INGRESO).estado(UPDATED_ESTADO).esAdministrador(UPDATED_ES_ADMINISTRADOR);

        restMiembroGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMiembroGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMiembroGrupo))
            )
            .andExpect(status().isOk());

        // Validate the MiembroGrupo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMiembroGrupoUpdatableFieldsEquals(partialUpdatedMiembroGrupo, getPersistedMiembroGrupo(partialUpdatedMiembroGrupo));
    }

    @Test
    void patchNonExistingMiembroGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miembroGrupo.setId(UUID.randomUUID().toString());

        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiembroGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, miembroGrupoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(miembroGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMiembroGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miembroGrupo.setId(UUID.randomUUID().toString());

        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiembroGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(miembroGrupoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMiembroGrupo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miembroGrupo.setId(UUID.randomUUID().toString());

        // Create the MiembroGrupo
        MiembroGrupoDTO miembroGrupoDTO = miembroGrupoMapper.toDto(miembroGrupo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiembroGrupoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(miembroGrupoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MiembroGrupo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMiembroGrupo() throws Exception {
        // Initialize the database
        insertedMiembroGrupo = miembroGrupoRepository.save(miembroGrupo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the miembroGrupo
        restMiembroGrupoMockMvc
            .perform(delete(ENTITY_API_URL_ID, miembroGrupo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return miembroGrupoRepository.count();
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

    protected MiembroGrupo getPersistedMiembroGrupo(MiembroGrupo miembroGrupo) {
        return miembroGrupoRepository.findById(miembroGrupo.getId()).orElseThrow();
    }

    protected void assertPersistedMiembroGrupoToMatchAllProperties(MiembroGrupo expectedMiembroGrupo) {
        assertMiembroGrupoAllPropertiesEquals(expectedMiembroGrupo, getPersistedMiembroGrupo(expectedMiembroGrupo));
    }

    protected void assertPersistedMiembroGrupoToMatchUpdatableProperties(MiembroGrupo expectedMiembroGrupo) {
        assertMiembroGrupoAllUpdatablePropertiesEquals(expectedMiembroGrupo, getPersistedMiembroGrupo(expectedMiembroGrupo));
    }
}
