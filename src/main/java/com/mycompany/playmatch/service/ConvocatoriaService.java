package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.ConvocatoriaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Convocatoria}.
 */
public interface ConvocatoriaService {
    /**
     * Save a convocatoria.
     *
     * @param convocatoriaDTO the entity to save.
     * @return the persisted entity.
     */
    ConvocatoriaDTO save(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Updates a convocatoria.
     *
     * @param convocatoriaDTO the entity to update.
     * @return the persisted entity.
     */
    ConvocatoriaDTO update(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Partially updates a convocatoria.
     *
     * @param convocatoriaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConvocatoriaDTO> partialUpdate(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Get all the convocatorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConvocatoriaDTO> findAll(Pageable pageable);

    /**
     * Get all the convocatorias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConvocatoriaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" convocatoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConvocatoriaDTO> findOne(String id);

    /**
     * Delete the "id" convocatoria.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
