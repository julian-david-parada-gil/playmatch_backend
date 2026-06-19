package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.CalendarioEventoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.CalendarioEvento}.
 */
public interface CalendarioEventoService {
    /**
     * Save a calendarioEvento.
     *
     * @param calendarioEventoDTO the entity to save.
     * @return the persisted entity.
     */
    CalendarioEventoDTO save(CalendarioEventoDTO calendarioEventoDTO);

    /**
     * Updates a calendarioEvento.
     *
     * @param calendarioEventoDTO the entity to update.
     * @return the persisted entity.
     */
    CalendarioEventoDTO update(CalendarioEventoDTO calendarioEventoDTO);

    /**
     * Partially updates a calendarioEvento.
     *
     * @param calendarioEventoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CalendarioEventoDTO> partialUpdate(CalendarioEventoDTO calendarioEventoDTO);

    /**
     * Get all the calendarioEventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalendarioEventoDTO> findAll(Pageable pageable);

    /**
     * Get all the calendarioEventos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalendarioEventoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" calendarioEvento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendarioEventoDTO> findOne(String id);

    /**
     * Delete the "id" calendarioEvento.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
