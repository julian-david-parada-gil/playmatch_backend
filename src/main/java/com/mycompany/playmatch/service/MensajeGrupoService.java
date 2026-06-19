package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.MensajeGrupoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.MensajeGrupo}.
 */
public interface MensajeGrupoService {
    /**
     * Save a mensajeGrupo.
     *
     * @param mensajeGrupoDTO the entity to save.
     * @return the persisted entity.
     */
    MensajeGrupoDTO save(MensajeGrupoDTO mensajeGrupoDTO);

    /**
     * Updates a mensajeGrupo.
     *
     * @param mensajeGrupoDTO the entity to update.
     * @return the persisted entity.
     */
    MensajeGrupoDTO update(MensajeGrupoDTO mensajeGrupoDTO);

    /**
     * Partially updates a mensajeGrupo.
     *
     * @param mensajeGrupoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MensajeGrupoDTO> partialUpdate(MensajeGrupoDTO mensajeGrupoDTO);

    /**
     * Get all the mensajeGrupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MensajeGrupoDTO> findAll(Pageable pageable);

    /**
     * Get all the mensajeGrupos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MensajeGrupoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mensajeGrupo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MensajeGrupoDTO> findOne(String id);

    /**
     * Delete the "id" mensajeGrupo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
