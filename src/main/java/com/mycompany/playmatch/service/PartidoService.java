package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.PartidoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Partido}.
 */
public interface PartidoService {
    /**
     * Save a partido.
     *
     * @param partidoDTO the entity to save.
     * @return the persisted entity.
     */
    PartidoDTO save(PartidoDTO partidoDTO);

    /**
     * Updates a partido.
     *
     * @param partidoDTO the entity to update.
     * @return the persisted entity.
     */
    PartidoDTO update(PartidoDTO partidoDTO);

    /**
     * Partially updates a partido.
     *
     * @param partidoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PartidoDTO> partialUpdate(PartidoDTO partidoDTO);

    /**
     * Get all the partidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartidoDTO> findAll(Pageable pageable);

    /**
     * Get all the partidos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartidoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" partido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartidoDTO> findOne(String id);

    /**
     * Delete the "id" partido.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
