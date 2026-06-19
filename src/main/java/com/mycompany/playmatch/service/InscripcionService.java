package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.InscripcionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Inscripcion}.
 */
public interface InscripcionService {
    /**
     * Save a inscripcion.
     *
     * @param inscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    InscripcionDTO save(InscripcionDTO inscripcionDTO);

    /**
     * Updates a inscripcion.
     *
     * @param inscripcionDTO the entity to update.
     * @return the persisted entity.
     */
    InscripcionDTO update(InscripcionDTO inscripcionDTO);

    /**
     * Partially updates a inscripcion.
     *
     * @param inscripcionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InscripcionDTO> partialUpdate(InscripcionDTO inscripcionDTO);

    /**
     * Get all the inscripcions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InscripcionDTO> findAll(Pageable pageable);

    /**
     * Get all the inscripcions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InscripcionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" inscripcion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InscripcionDTO> findOne(String id);

    /**
     * Delete the "id" inscripcion.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
