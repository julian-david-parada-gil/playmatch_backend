package com.mycompany.playmatch.service;

import com.mycompany.playmatch.service.dto.NoticiaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.playmatch.domain.Noticia}.
 */
public interface NoticiaService {
    /**
     * Save a noticia.
     *
     * @param noticiaDTO the entity to save.
     * @return the persisted entity.
     */
    NoticiaDTO save(NoticiaDTO noticiaDTO);

    /**
     * Updates a noticia.
     *
     * @param noticiaDTO the entity to update.
     * @return the persisted entity.
     */
    NoticiaDTO update(NoticiaDTO noticiaDTO);

    /**
     * Partially updates a noticia.
     *
     * @param noticiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NoticiaDTO> partialUpdate(NoticiaDTO noticiaDTO);

    /**
     * Get all the noticias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoticiaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" noticia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoticiaDTO> findOne(String id);

    /**
     * Delete the "id" noticia.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
