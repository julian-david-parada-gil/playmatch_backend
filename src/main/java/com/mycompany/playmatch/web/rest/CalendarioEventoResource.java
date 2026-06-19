package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.CalendarioEventoRepository;
import com.mycompany.playmatch.service.CalendarioEventoService;
import com.mycompany.playmatch.service.dto.CalendarioEventoDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.CalendarioEvento}.
 */
@RestController
@RequestMapping("/api/calendario-eventos")
public class CalendarioEventoResource {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarioEventoResource.class);

    private static final String ENTITY_NAME = "calendarioEvento";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final CalendarioEventoService calendarioEventoService;

    private final CalendarioEventoRepository calendarioEventoRepository;

    public CalendarioEventoResource(
        CalendarioEventoService calendarioEventoService,
        CalendarioEventoRepository calendarioEventoRepository
    ) {
        this.calendarioEventoService = calendarioEventoService;
        this.calendarioEventoRepository = calendarioEventoRepository;
    }

    /**
     * {@code POST  /calendario-eventos} : Create a new calendarioEvento.
     *
     * @param calendarioEventoDTO the calendarioEventoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendarioEventoDTO, or with status {@code 400 (Bad Request)} if the calendarioEvento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CalendarioEventoDTO> createCalendarioEvento(@Valid @RequestBody CalendarioEventoDTO calendarioEventoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CalendarioEvento : {}", calendarioEventoDTO);
        if (calendarioEventoDTO.getId() != null) {
            throw new BadRequestAlertException("A new calendarioEvento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calendarioEventoDTO = calendarioEventoService.save(calendarioEventoDTO);
        return ResponseEntity.created(new URI("/api/calendario-eventos/" + calendarioEventoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, calendarioEventoDTO.getId()))
            .body(calendarioEventoDTO);
    }

    /**
     * {@code PUT  /calendario-eventos/:id} : Updates an existing calendarioEvento.
     *
     * @param id the id of the calendarioEventoDTO to save.
     * @param calendarioEventoDTO the calendarioEventoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarioEventoDTO,
     * or with status {@code 400 (Bad Request)} if the calendarioEventoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendarioEventoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalendarioEventoDTO> updateCalendarioEvento(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CalendarioEventoDTO calendarioEventoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CalendarioEvento : {}, {}", id, calendarioEventoDTO);
        if (calendarioEventoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendarioEventoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calendarioEventoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        calendarioEventoDTO = calendarioEventoService.update(calendarioEventoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calendarioEventoDTO.getId()))
            .body(calendarioEventoDTO);
    }

    /**
     * {@code PATCH  /calendario-eventos/:id} : Partial updates given fields of an existing calendarioEvento, field will ignore if it is null
     *
     * @param id the id of the calendarioEventoDTO to save.
     * @param calendarioEventoDTO the calendarioEventoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarioEventoDTO,
     * or with status {@code 400 (Bad Request)} if the calendarioEventoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calendarioEventoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calendarioEventoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalendarioEventoDTO> partialUpdateCalendarioEvento(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CalendarioEventoDTO calendarioEventoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CalendarioEvento partially : {}, {}", id, calendarioEventoDTO);
        if (calendarioEventoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendarioEventoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calendarioEventoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalendarioEventoDTO> result = calendarioEventoService.partialUpdate(calendarioEventoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calendarioEventoDTO.getId())
        );
    }

    /**
     * {@code GET  /calendario-eventos} : get all the Calendario Eventos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Calendario Eventos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CalendarioEventoDTO>> getAllCalendarioEventos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of CalendarioEventos");
        Page<CalendarioEventoDTO> page;
        if (eagerload) {
            page = calendarioEventoService.findAllWithEagerRelationships(pageable);
        } else {
            page = calendarioEventoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calendario-eventos/:id} : get the "id" calendarioEvento.
     *
     * @param id the id of the calendarioEventoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarioEventoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalendarioEventoDTO> getCalendarioEvento(@PathVariable("id") String id) {
        LOG.debug("REST request to get CalendarioEvento : {}", id);
        Optional<CalendarioEventoDTO> calendarioEventoDTO = calendarioEventoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarioEventoDTO);
    }

    /**
     * {@code DELETE  /calendario-eventos/:id} : delete the "id" calendarioEvento.
     *
     * @param id the id of the calendarioEventoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarioEvento(@PathVariable("id") String id) {
        LOG.debug("REST request to delete CalendarioEvento : {}", id);
        calendarioEventoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
