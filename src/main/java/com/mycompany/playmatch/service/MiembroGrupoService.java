package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.MiembroGrupoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.MiembroGrupo}.
 */
public interface MiembroGrupoService {
    /**
     * Save a miembroGrupo.
     *
     * @param miembroGrupoDTO the entity to save.
     * @return the persisted entity.
     */
    MiembroGrupoDTO save(MiembroGrupoDTO miembroGrupoDTO);

    /**
     * Updates a miembroGrupo.
     *
     * @param miembroGrupoDTO the entity to update.
     * @return the persisted entity.
     */
    MiembroGrupoDTO update(MiembroGrupoDTO miembroGrupoDTO);

    /**
     * Partially updates a miembroGrupo.
     *
     * @param miembroGrupoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MiembroGrupoDTO> partialUpdate(MiembroGrupoDTO miembroGrupoDTO);

    /**
     * Get all the miembroGrupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MiembroGrupoDTO> findAll(Pageable pageable);

    /**
     * Get all the miembroGrupos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MiembroGrupoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" miembroGrupo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MiembroGrupoDTO> findOne(String id);

    /**
     * Delete the "id" miembroGrupo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
