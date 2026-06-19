package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.CalificacionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Calificacion}.
 */
public interface CalificacionService {
    /**
     * Save a calificacion.
     *
     * @param calificacionDTO the entity to save.
     * @return the persisted entity.
     */
    CalificacionDTO save(CalificacionDTO calificacionDTO);

    /**
     * Updates a calificacion.
     *
     * @param calificacionDTO the entity to update.
     * @return the persisted entity.
     */
    CalificacionDTO update(CalificacionDTO calificacionDTO);

    /**
     * Partially updates a calificacion.
     *
     * @param calificacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CalificacionDTO> partialUpdate(CalificacionDTO calificacionDTO);

    /**
     * Get all the calificacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalificacionDTO> findAll(Pageable pageable);

    /**
     * Get all the calificacions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalificacionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" calificacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalificacionDTO> findOne(String id);

    /**
     * Delete the "id" calificacion.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
