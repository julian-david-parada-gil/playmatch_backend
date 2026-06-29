package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.NoticiaRepository;
import com.mycompany.playmatch.service.NoticiaService;
import com.mycompany.playmatch.service.dto.NoticiaDTO;
import com.mycompany.playmatch.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.playmatch.domain.Noticia}.
 */
@RestController
@RequestMapping("/api/noticias")
public class NoticiaResource {

    private static final Logger LOG = LoggerFactory.getLogger(NoticiaResource.class);

    private static final String ENTITY_NAME = "noticia";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final NoticiaService noticiaService;

    private final NoticiaRepository noticiaRepository;

    public NoticiaResource(NoticiaService noticiaService, NoticiaRepository noticiaRepository) {
        this.noticiaService = noticiaService;
        this.noticiaRepository = noticiaRepository;
    }

    /**
     * {@code POST  /noticias} : Create a new noticia.
     *
     * @param noticiaDTO the noticiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticiaDTO, or with status {@code 400 (Bad Request)} if the noticia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<NoticiaDTO> createNoticia(@Valid @RequestBody NoticiaDTO noticiaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Noticia : {}", noticiaDTO);
        if (noticiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        noticiaDTO = noticiaService.save(noticiaDTO);
        return ResponseEntity.created(new URI("/api/noticias/" + noticiaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, noticiaDTO.getId()))
            .body(noticiaDTO);
    }

    /**
     * {@code PUT  /noticias/:id} : Updates an existing noticia.
     *
     * @param id the id of the noticiaDTO to save.
     * @param noticiaDTO the noticiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticiaDTO,
     * or with status {@code 400 (Bad Request)} if the noticiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<NoticiaDTO> updateNoticia(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody NoticiaDTO noticiaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Noticia : {}, {}", id, noticiaDTO);
        if (noticiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        noticiaDTO = noticiaService.update(noticiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, noticiaDTO.getId()))
            .body(noticiaDTO);
    }

    /**
     * {@code PATCH  /noticias/:id} : Partial updates given fields of an existing noticia, field will ignore if it is null
     *
     * @param id the id of the noticiaDTO to save.
     * @param noticiaDTO the noticiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticiaDTO,
     * or with status {@code 400 (Bad Request)} if the noticiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the noticiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the noticiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NoticiaDTO> partialUpdateNoticia(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody NoticiaDTO noticiaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Noticia partially : {}, {}", id, noticiaDTO);
        if (noticiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NoticiaDTO> result = noticiaService.partialUpdate(noticiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, noticiaDTO.getId())
        );
    }

    /**
     * {@code GET  /noticias} : get all the Noticias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Noticias in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<List<NoticiaDTO>> getAllNoticias(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Noticias");
        Page<NoticiaDTO> page = noticiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticia.
     *
     * @param id the id of the noticiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<NoticiaDTO> getNoticia(@PathVariable("id") String id) {
        LOG.debug("REST request to get Noticia : {}", id);
        Optional<NoticiaDTO> noticiaDTO = noticiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticiaDTO);
    }

    /**
     * {@code DELETE  /noticias/:id} : delete the "id" noticia.
     *
     * @param id the id of the noticiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteNoticia(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Noticia : {}", id);
        noticiaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
