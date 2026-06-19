package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.TablaPosicionRepository;
import com.mycompany.playmatch.service.TablaPosicionService;
import com.mycompany.playmatch.service.dto.TablaPosicionDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.TablaPosicion}.
 */
@RestController
@RequestMapping("/api/tabla-posicions")
public class TablaPosicionResource {

    private static final Logger LOG = LoggerFactory.getLogger(TablaPosicionResource.class);

    private static final String ENTITY_NAME = "tablaPosicion";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final TablaPosicionService tablaPosicionService;

    private final TablaPosicionRepository tablaPosicionRepository;

    public TablaPosicionResource(TablaPosicionService tablaPosicionService, TablaPosicionRepository tablaPosicionRepository) {
        this.tablaPosicionService = tablaPosicionService;
        this.tablaPosicionRepository = tablaPosicionRepository;
    }

    /**
     * {@code POST  /tabla-posicions} : Create a new tablaPosicion.
     *
     * @param tablaPosicionDTO the tablaPosicionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tablaPosicionDTO, or with status {@code 400 (Bad Request)} if the tablaPosicion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TablaPosicionDTO> createTablaPosicion(@Valid @RequestBody TablaPosicionDTO tablaPosicionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TablaPosicion : {}", tablaPosicionDTO);
        if (tablaPosicionDTO.getId() != null) {
            throw new BadRequestAlertException("A new tablaPosicion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tablaPosicionDTO = tablaPosicionService.save(tablaPosicionDTO);
        return ResponseEntity.created(new URI("/api/tabla-posicions/" + tablaPosicionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tablaPosicionDTO.getId()))
            .body(tablaPosicionDTO);
    }

    /**
     * {@code PUT  /tabla-posicions/:id} : Updates an existing tablaPosicion.
     *
     * @param id the id of the tablaPosicionDTO to save.
     * @param tablaPosicionDTO the tablaPosicionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tablaPosicionDTO,
     * or with status {@code 400 (Bad Request)} if the tablaPosicionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tablaPosicionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TablaPosicionDTO> updateTablaPosicion(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody TablaPosicionDTO tablaPosicionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TablaPosicion : {}, {}", id, tablaPosicionDTO);
        if (tablaPosicionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tablaPosicionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tablaPosicionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tablaPosicionDTO = tablaPosicionService.update(tablaPosicionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tablaPosicionDTO.getId()))
            .body(tablaPosicionDTO);
    }

    /**
     * {@code PATCH  /tabla-posicions/:id} : Partial updates given fields of an existing tablaPosicion, field will ignore if it is null
     *
     * @param id the id of the tablaPosicionDTO to save.
     * @param tablaPosicionDTO the tablaPosicionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tablaPosicionDTO,
     * or with status {@code 400 (Bad Request)} if the tablaPosicionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tablaPosicionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tablaPosicionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TablaPosicionDTO> partialUpdateTablaPosicion(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody TablaPosicionDTO tablaPosicionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TablaPosicion partially : {}, {}", id, tablaPosicionDTO);
        if (tablaPosicionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tablaPosicionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tablaPosicionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TablaPosicionDTO> result = tablaPosicionService.partialUpdate(tablaPosicionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tablaPosicionDTO.getId())
        );
    }

    /**
     * {@code GET  /tabla-posicions} : get all the Tabla Posicions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Tabla Posicions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TablaPosicionDTO>> getAllTablaPosicions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of TablaPosicions");
        Page<TablaPosicionDTO> page;
        if (eagerload) {
            page = tablaPosicionService.findAllWithEagerRelationships(pageable);
        } else {
            page = tablaPosicionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tabla-posicions/:id} : get the "id" tablaPosicion.
     *
     * @param id the id of the tablaPosicionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tablaPosicionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TablaPosicionDTO> getTablaPosicion(@PathVariable("id") String id) {
        LOG.debug("REST request to get TablaPosicion : {}", id);
        Optional<TablaPosicionDTO> tablaPosicionDTO = tablaPosicionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tablaPosicionDTO);
    }

    /**
     * {@code DELETE  /tabla-posicions/:id} : delete the "id" tablaPosicion.
     *
     * @param id the id of the tablaPosicionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTablaPosicion(@PathVariable("id") String id) {
        LOG.debug("REST request to delete TablaPosicion : {}", id);
        tablaPosicionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
