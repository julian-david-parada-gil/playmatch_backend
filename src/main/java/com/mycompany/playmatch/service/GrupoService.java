package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.GrupoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Grupo}.
 */
public interface GrupoService {
    /**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save.
     * @return the persisted entity.
     */
    GrupoDTO save(GrupoDTO grupoDTO);

    /**
     * Updates a grupo.
     *
     * @param grupoDTO the entity to update.
     * @return the persisted entity.
     */
    GrupoDTO update(GrupoDTO grupoDTO);

    /**
     * Partially updates a grupo.
     *
     * @param grupoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GrupoDTO> partialUpdate(GrupoDTO grupoDTO);

    /**
     * Get all the grupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GrupoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" grupo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GrupoDTO> findOne(String id);

    /**
     * Delete the "id" grupo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
