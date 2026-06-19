package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.InscripcionRepository;
import com.mycompany.playmatch.service.InscripcionService;
import com.mycompany.playmatch.service.dto.InscripcionDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.Inscripcion}.
 */
@RestController
@RequestMapping("/api/inscripcions")
public class InscripcionResource {

    private static final Logger LOG = LoggerFactory.getLogger(InscripcionResource.class);

    private static final String ENTITY_NAME = "inscripcion";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final InscripcionService inscripcionService;

    private final InscripcionRepository inscripcionRepository;

    public InscripcionResource(InscripcionService inscripcionService, InscripcionRepository inscripcionRepository) {
        this.inscripcionService = inscripcionService;
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * {@code POST  /inscripcions} : Create a new inscripcion.
     *
     * @param inscripcionDTO the inscripcionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inscripcionDTO, or with status {@code 400 (Bad Request)} if the inscripcion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InscripcionDTO> createInscripcion(@Valid @RequestBody InscripcionDTO inscripcionDTO) throws URISyntaxException {
        LOG.debug("REST request to save Inscripcion : {}", inscripcionDTO);
        if (inscripcionDTO.getId() != null) {
            throw new BadRequestAlertException("A new inscripcion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inscripcionDTO = inscripcionService.save(inscripcionDTO);
        return ResponseEntity.created(new URI("/api/inscripcions/" + inscripcionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inscripcionDTO.getId()))
            .body(inscripcionDTO);
    }

    /**
     * {@code PUT  /inscripcions/:id} : Updates an existing inscripcion.
     *
     * @param id the id of the inscripcionDTO to save.
     * @param inscripcionDTO the inscripcionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscripcionDTO,
     * or with status {@code 400 (Bad Request)} if the inscripcionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inscripcionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> updateInscripcion(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody InscripcionDTO inscripcionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Inscripcion : {}, {}", id, inscripcionDTO);
        if (inscripcionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscripcionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscripcionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inscripcionDTO = inscripcionService.update(inscripcionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inscripcionDTO.getId()))
            .body(inscripcionDTO);
    }

    /**
     * {@code PATCH  /inscripcions/:id} : Partial updates given fields of an existing inscripcion, field will ignore if it is null
     *
     * @param id the id of the inscripcionDTO to save.
     * @param inscripcionDTO the inscripcionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscripcionDTO,
     * or with status {@code 400 (Bad Request)} if the inscripcionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inscripcionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inscripcionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InscripcionDTO> partialUpdateInscripcion(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody InscripcionDTO inscripcionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Inscripcion partially : {}, {}", id, inscripcionDTO);
        if (inscripcionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscripcionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscripcionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InscripcionDTO> result = inscripcionService.partialUpdate(inscripcionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inscripcionDTO.getId())
        );
    }

    /**
     * {@code GET  /inscripcions} : get all the Inscripcions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Inscripcions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InscripcionDTO>> getAllInscripcions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Inscripcions");
        Page<InscripcionDTO> page;
        if (eagerload) {
            page = inscripcionService.findAllWithEagerRelationships(pageable);
        } else {
            page = inscripcionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inscripcions/:id} : get the "id" inscripcion.
     *
     * @param id the id of the inscripcionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inscripcionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> getInscripcion(@PathVariable("id") String id) {
        LOG.debug("REST request to get Inscripcion : {}", id);
        Optional<InscripcionDTO> inscripcionDTO = inscripcionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscripcionDTO);
    }

    /**
     * {@code DELETE  /inscripcions/:id} : delete the "id" inscripcion.
     *
     * @param id the id of the inscripcionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Inscripcion : {}", id);
        inscripcionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
