package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.CalificacionRepository;
import com.mycompany.playmatch.security.AuthoritiesConstants;
import com.mycompany.playmatch.service.CalificacionService;
import com.mycompany.playmatch.service.dto.CalificacionDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.Calificacion}.
 */
@RestController
@RequestMapping("/api/calificacions")
public class CalificacionResource {

    private static final Logger LOG = LoggerFactory.getLogger(CalificacionResource.class);

    private static final String ENTITY_NAME = "calificacion";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final CalificacionService calificacionService;

    private final CalificacionRepository calificacionRepository;

    public CalificacionResource(CalificacionService calificacionService, CalificacionRepository calificacionRepository) {
        this.calificacionService = calificacionService;
        this.calificacionRepository = calificacionRepository;
    }

    /**
     * {@code POST  /calificacions} : Create a new calificacion.
     *
     * @param calificacionDTO the calificacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calificacionDTO, or with status {@code 400 (Bad Request)} if the calificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") ")
    public ResponseEntity<CalificacionDTO> createCalificacion(@Valid @RequestBody CalificacionDTO calificacionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Calificacion : {}", calificacionDTO);
        if (calificacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new calificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calificacionDTO = calificacionService.save(calificacionDTO);
        return ResponseEntity.created(new URI("/api/calificacions/" + calificacionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, calificacionDTO.getId()))
            .body(calificacionDTO);
    }

    /**
     * {@code PUT  /calificacions/:id} : Updates an existing calificacion.
     *
     * @param id the id of the calificacionDTO to save.
     * @param calificacionDTO the calificacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calificacionDTO,
     * or with status {@code 400 (Bad Request)} if the calificacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calificacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalificacionDTO> updateCalificacion(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CalificacionDTO calificacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Calificacion : {}, {}", id, calificacionDTO);
        if (calificacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calificacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calificacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        calificacionDTO = calificacionService.update(calificacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calificacionDTO.getId()))
            .body(calificacionDTO);
    }

    /**
     * {@code PATCH  /calificacions/:id} : Partial updates given fields of an existing calificacion, field will ignore if it is null
     *
     * @param id the id of the calificacionDTO to save.
     * @param calificacionDTO the calificacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calificacionDTO,
     * or with status {@code 400 (Bad Request)} if the calificacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calificacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calificacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalificacionDTO> partialUpdateCalificacion(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CalificacionDTO calificacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Calificacion partially : {}, {}", id, calificacionDTO);
        if (calificacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calificacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calificacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalificacionDTO> result = calificacionService.partialUpdate(calificacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calificacionDTO.getId())
        );
    }

    /**
     * {@code GET  /calificacions} : get all the Calificacions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Calificacions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CalificacionDTO>> getAllCalificacions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Calificacions");
        Page<CalificacionDTO> page;
        if (eagerload) {
            page = calificacionService.findAllWithEagerRelationships(pageable);
        } else {
            page = calificacionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calificacions/:id} : get the "id" calificacion.
     *
     * @param id the id of the calificacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calificacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionDTO> getCalificacion(@PathVariable("id") String id) {
        LOG.debug("REST request to get Calificacion : {}", id);
        Optional<CalificacionDTO> calificacionDTO = calificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calificacionDTO);
    }

    /**
     * {@code DELETE  /calificacions/:id} : delete the "id" calificacion.
     *
     * @param id the id of the calificacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacion(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Calificacion : {}", id);
        calificacionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
