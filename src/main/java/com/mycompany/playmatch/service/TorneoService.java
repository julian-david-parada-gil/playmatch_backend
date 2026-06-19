package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.TorneoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Torneo}.
 */
public interface TorneoService {
    /**
     * Save a torneo.
     *
     * @param torneoDTO the entity to save.
     * @return the persisted entity.
     */
    TorneoDTO save(TorneoDTO torneoDTO);

    /**
     * Updates a torneo.
     *
     * @param torneoDTO the entity to update.
     * @return the persisted entity.
     */
    TorneoDTO update(TorneoDTO torneoDTO);

    /**
     * Partially updates a torneo.
     *
     * @param torneoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TorneoDTO> partialUpdate(TorneoDTO torneoDTO);

    /**
     * Get all the torneos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TorneoDTO> findAll(Pageable pageable);

    /**
     * Get all the torneos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TorneoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" torneo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TorneoDTO> findOne(String id);

    /**
     * Delete the "id" torneo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
