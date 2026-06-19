package com.mycompany.playmatch.web.rest;

import static com.mycompany.playmatch.domain.NoticiaAsserts.*;
import static com.mycompany.playmatch.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.playmatch.IntegrationTest;
import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Noticia;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.TipoNoticia;
import com.mycompany.playmatch.repository.NoticiaRepository;
import com.mycompany.playmatch.service.dto.NoticiaDTO;
import com.mycompany.playmatch.service.mapper.NoticiaMapper;
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
 * Integration tests for the {@link NoticiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoticiaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final TipoNoticia DEFAULT_TIPO = TipoNoticia.NOTICIA;
    private static final TipoNoticia UPDATED_TIPO = TipoNoticia.CONVOCATORIA;

    private static final Instant DEFAULT_FECHA_PUBLICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PUBLICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoGeneral DEFAULT_ESTADO = EstadoGeneral.ACTIVO;
    private static final EstadoGeneral UPDATED_ESTADO = EstadoGeneral.INACTIVO;

    private static final String ENTITY_API_URL = "/api/noticias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private NoticiaMapper noticiaMapper;

    @Autowired
    private MockMvc restNoticiaMockMvc;

    private Noticia noticia;

    private Noticia insertedNoticia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createEntity() {
        Noticia noticia = new Noticia()
            .titulo(DEFAULT_TITULO)
            .contenido(DEFAULT_CONTENIDO)
            .tipo(DEFAULT_TIPO)
            .fechaPublicacion(DEFAULT_FECHA_PUBLICACION)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createEntity();
        cuenta.setId("fixed-id-for-tests");
        noticia.setAutor(cuenta);
        return noticia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createUpdatedEntity() {
        Noticia updatedNoticia = new Noticia()
            .titulo(UPDATED_TITULO)
            .contenido(UPDATED_CONTENIDO)
            .tipo(UPDATED_TIPO)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Cuenta cuenta;
        cuenta = CuentaResourceIT.createUpdatedEntity();
        cuenta.setId("fixed-id-for-tests");
        updatedNoticia.setAutor(cuenta);
        return updatedNoticia;
    }

    @BeforeEach
    void initTest() {
        noticia = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedNoticia != null) {
            noticiaRepository.delete(insertedNoticia);
            insertedNoticia = null;
        }
    }

    @Test
    void createNoticia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);
        var returnedNoticiaDTO = om.readValue(
            restNoticiaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NoticiaDTO.class
        );

        // Validate the Noticia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNoticia = noticiaMapper.toEntity(returnedNoticiaDTO);
        assertNoticiaUpdatableFieldsEquals(returnedNoticia, getPersistedNoticia(returnedNoticia));

        insertedNoticia = returnedNoticia;
    }

    @Test
    void createNoticiaWithExistingId() throws Exception {
        // Create the Noticia with an existing ID
        noticia.setId("existing_id");
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        noticia.setTitulo(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        noticia.setTipo(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaPublicacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        noticia.setFechaPublicacion(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        noticia.setEstado(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllNoticias() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.save(noticia);

        // Get all the noticiaList
        restNoticiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    void getNoticia() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.save(noticia);

        // Get the noticia
        restNoticiaMockMvc
            .perform(get(ENTITY_API_URL_ID, noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticia.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingNoticia() throws Exception {
        // Get the noticia
        restNoticiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingNoticia() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.save(noticia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the noticia
        Noticia updatedNoticia = noticiaRepository.findById(noticia.getId()).orElseThrow();
        updatedNoticia
            .titulo(UPDATED_TITULO)
            .contenido(UPDATED_CONTENIDO)
            .tipo(UPDATED_TIPO)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .estado(UPDATED_ESTADO);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(updatedNoticia);

        restNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticiaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNoticiaToMatchAllProperties(updatedNoticia);
    }

    @Test
    void putNonExistingNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(UUID.randomUUID().toString());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticiaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(UUID.randomUUID().toString());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(UUID.randomUUID().toString());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNoticiaWithPatch() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.save(noticia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the noticia using partial update
        Noticia partialUpdatedNoticia = new Noticia();
        partialUpdatedNoticia.setId(noticia.getId());

        partialUpdatedNoticia.titulo(UPDATED_TITULO).contenido(UPDATED_CONTENIDO);

        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNoticia))
            )
            .andExpect(status().isOk());

        // Validate the Noticia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNoticiaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNoticia, noticia), getPersistedNoticia(noticia));
    }

    @Test
    void fullUpdateNoticiaWithPatch() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.save(noticia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the noticia using partial update
        Noticia partialUpdatedNoticia = new Noticia();
        partialUpdatedNoticia.setId(noticia.getId());

        partialUpdatedNoticia
            .titulo(UPDATED_TITULO)
            .contenido(UPDATED_CONTENIDO)
            .tipo(UPDATED_TIPO)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .estado(UPDATED_ESTADO);

        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNoticia))
            )
            .andExpect(status().isOk());

        // Validate the Noticia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNoticiaUpdatableFieldsEquals(partialUpdatedNoticia, getPersistedNoticia(partialUpdatedNoticia));
    }

    @Test
    void patchNonExistingNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(UUID.randomUUID().toString());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noticiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(UUID.randomUUID().toString());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(UUID.randomUUID().toString());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNoticia() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.save(noticia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the noticia
        restNoticiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, noticia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return noticiaRepository.count();
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

    protected Noticia getPersistedNoticia(Noticia noticia) {
        return noticiaRepository.findById(noticia.getId()).orElseThrow();
    }

    protected void assertPersistedNoticiaToMatchAllProperties(Noticia expectedNoticia) {
        assertNoticiaAllPropertiesEquals(expectedNoticia, getPersistedNoticia(expectedNoticia));
    }

    protected void assertPersistedNoticiaToMatchUpdatableProperties(Noticia expectedNoticia) {
        assertNoticiaAllUpdatablePropertiesEquals(expectedNoticia, getPersistedNoticia(expectedNoticia));
    }
}
