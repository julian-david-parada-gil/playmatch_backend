package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.EncuestaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Encuesta}.
 */
public interface EncuestaService {
    /**
     * Save a encuesta.
     *
     * @param encuestaDTO the entity to save.
     * @return the persisted entity.
     */
    EncuestaDTO save(EncuestaDTO encuestaDTO);

    /**
     * Updates a encuesta.
     *
     * @param encuestaDTO the entity to update.
     * @return the persisted entity.
     */
    EncuestaDTO update(EncuestaDTO encuestaDTO);

    /**
     * Partially updates a encuesta.
     *
     * @param encuestaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EncuestaDTO> partialUpdate(EncuestaDTO encuestaDTO);

    /**
     * Get all the encuestas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EncuestaDTO> findAll(Pageable pageable);

    /**
     * Get all the encuestas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EncuestaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" encuesta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EncuestaDTO> findOne(String id);

    /**
     * Delete the "id" encuesta.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
