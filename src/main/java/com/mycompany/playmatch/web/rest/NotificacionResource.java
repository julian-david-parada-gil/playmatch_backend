package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.NotificacionRepository;
import com.mycompany.playmatch.security.AuthoritiesConstants;
import com.mycompany.playmatch.service.NotificacionService;
import com.mycompany.playmatch.service.dto.NotificacionDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.playmatch.domain.Notificacion}.
 */
@RestController
@RequestMapping("/api/notificacions")
public class NotificacionResource {

    private static final Logger LOG = LoggerFactory.getLogger(NotificacionResource.class);

    private static final String ENTITY_NAME = "notificacion";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final NotificacionService notificacionService;

    private final NotificacionRepository notificacionRepository;

    public NotificacionResource(NotificacionService notificacionService, NotificacionRepository notificacionRepository) {
        this.notificacionService = notificacionService;
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * {@code POST  /notificacions} : Create a new notificacion.
     *
     * @param notificacionDTO the notificacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacionDTO, or with status {@code 400 (Bad Request)} if the notificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<NotificacionDTO> createNotificacion(@Valid @RequestBody NotificacionDTO notificacionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Notificacion : {}", notificacionDTO);
        if (notificacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        notificacionDTO = notificacionService.save(notificacionDTO);
        return ResponseEntity.created(new URI("/api/notificacions/" + notificacionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, notificacionDTO.getId()))
            .body(notificacionDTO);
    }

    /**
     * {@code PUT  /notificacions/:id} : Updates an existing notificacion.
     *
     * @param id the id of the notificacionDTO to save.
     * @param notificacionDTO the notificacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificacionDTO,
     * or with status {@code 400 (Bad Request)} if the notificacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<NotificacionDTO> updateNotificacion(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody NotificacionDTO notificacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Notificacion : {}, {}", id, notificacionDTO);
        if (notificacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        notificacionDTO = notificacionService.update(notificacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificacionDTO.getId()))
            .body(notificacionDTO);
    }

    /**
     * {@code PATCH  /notificacions/:id} : Partial updates given fields of an existing notificacion, field will ignore if it is null
     *
     * @param id the id of the notificacionDTO to save.
     * @param notificacionDTO the notificacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificacionDTO,
     * or with status {@code 400 (Bad Request)} if the notificacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notificacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificacionDTO> partialUpdateNotificacion(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody NotificacionDTO notificacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Notificacion partially : {}, {}", id, notificacionDTO);
        if (notificacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificacionDTO> result = notificacionService.partialUpdate(notificacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificacionDTO.getId())
        );
    }

    /**
     * {@code GET  /notificacions} : get all the Notificacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Notificacions in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<NotificacionDTO>> getAllNotificacions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Notificacions");
        Page<NotificacionDTO> page = notificacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notificacions/:id} : get the "id" notificacion.
     *
     * @param id the id of the notificacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable("id") String id) {
        LOG.debug("REST request to get Notificacion : {}", id);
        Optional<NotificacionDTO> notificacionDTO = notificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificacionDTO);
    }

    /**
     * {@code DELETE  /notificacions/:id} : delete the "id" notificacion.
     *
     * @param id the id of the notificacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Notificacion : {}", id);
        notificacionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
