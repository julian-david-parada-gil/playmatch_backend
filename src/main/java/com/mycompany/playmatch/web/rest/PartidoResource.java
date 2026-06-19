package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.PartidoRepository;
import com.mycompany.playmatch.service.PartidoService;
import com.mycompany.playmatch.service.dto.PartidoDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.Partido}.
 */
@RestController
@RequestMapping("/api/partidos")
public class PartidoResource {

    private static final Logger LOG = LoggerFactory.getLogger(PartidoResource.class);

    private static final String ENTITY_NAME = "partido";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final PartidoService partidoService;

    private final PartidoRepository partidoRepository;

    public PartidoResource(PartidoService partidoService, PartidoRepository partidoRepository) {
        this.partidoService = partidoService;
        this.partidoRepository = partidoRepository;
    }

    /**
     * {@code POST  /partidos} : Create a new partido.
     *
     * @param partidoDTO the partidoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partidoDTO, or with status {@code 400 (Bad Request)} if the partido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartidoDTO> createPartido(@Valid @RequestBody PartidoDTO partidoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Partido : {}", partidoDTO);
        if (partidoDTO.getId() != null) {
            throw new BadRequestAlertException("A new partido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partidoDTO = partidoService.save(partidoDTO);
        return ResponseEntity.created(new URI("/api/partidos/" + partidoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, partidoDTO.getId()))
            .body(partidoDTO);
    }

    /**
     * {@code PUT  /partidos/:id} : Updates an existing partido.
     *
     * @param id the id of the partidoDTO to save.
     * @param partidoDTO the partidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partidoDTO,
     * or with status {@code 400 (Bad Request)} if the partidoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartidoDTO> updatePartido(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PartidoDTO partidoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Partido : {}, {}", id, partidoDTO);
        if (partidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partidoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partidoDTO = partidoService.update(partidoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partidoDTO.getId()))
            .body(partidoDTO);
    }

    /**
     * {@code PATCH  /partidos/:id} : Partial updates given fields of an existing partido, field will ignore if it is null
     *
     * @param id the id of the partidoDTO to save.
     * @param partidoDTO the partidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partidoDTO,
     * or with status {@code 400 (Bad Request)} if the partidoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partidoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartidoDTO> partialUpdatePartido(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PartidoDTO partidoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Partido partially : {}, {}", id, partidoDTO);
        if (partidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partidoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartidoDTO> result = partidoService.partialUpdate(partidoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partidoDTO.getId())
        );
    }

    /**
     * {@code GET  /partidos} : get all the Partidos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Partidos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartidoDTO>> getAllPartidos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Partidos");
        Page<PartidoDTO> page;
        if (eagerload) {
            page = partidoService.findAllWithEagerRelationships(pageable);
        } else {
            page = partidoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partidos/:id} : get the "id" partido.
     *
     * @param id the id of the partidoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partidoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getPartido(@PathVariable("id") String id) {
        LOG.debug("REST request to get Partido : {}", id);
        Optional<PartidoDTO> partidoDTO = partidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partidoDTO);
    }

    /**
     * {@code DELETE  /partidos/:id} : delete the "id" partido.
     *
     * @param id the id of the partidoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Partido : {}", id);
        partidoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
