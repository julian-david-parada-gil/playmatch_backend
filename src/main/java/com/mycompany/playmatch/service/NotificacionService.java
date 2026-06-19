package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.NotificacionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Notificacion}.
 */
public interface NotificacionService {
    /**
     * Save a notificacion.
     *
     * @param notificacionDTO the entity to save.
     * @return the persisted entity.
     */
    NotificacionDTO save(NotificacionDTO notificacionDTO);

    /**
     * Updates a notificacion.
     *
     * @param notificacionDTO the entity to update.
     * @return the persisted entity.
     */
    NotificacionDTO update(NotificacionDTO notificacionDTO);

    /**
     * Partially updates a notificacion.
     *
     * @param notificacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotificacionDTO> partialUpdate(NotificacionDTO notificacionDTO);

    /**
     * Get all the notificacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotificacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" notificacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificacionDTO> findOne(String id);

    /**
     * Delete the "id" notificacion.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
