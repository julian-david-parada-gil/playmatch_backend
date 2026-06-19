package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.TablaPosicionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.TablaPosicion}.
 */
public interface TablaPosicionService {
    /**
     * Save a tablaPosicion.
     *
     * @param tablaPosicionDTO the entity to save.
     * @return the persisted entity.
     */
    TablaPosicionDTO save(TablaPosicionDTO tablaPosicionDTO);

    /**
     * Updates a tablaPosicion.
     *
     * @param tablaPosicionDTO the entity to update.
     * @return the persisted entity.
     */
    TablaPosicionDTO update(TablaPosicionDTO tablaPosicionDTO);

    /**
     * Partially updates a tablaPosicion.
     *
     * @param tablaPosicionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TablaPosicionDTO> partialUpdate(TablaPosicionDTO tablaPosicionDTO);

    /**
     * Get all the tablaPosicions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TablaPosicionDTO> findAll(Pageable pageable);

    /**
     * Get all the tablaPosicions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TablaPosicionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tablaPosicion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TablaPosicionDTO> findOne(String id);

    /**
     * Delete the "id" tablaPosicion.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
