package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.EncuestaRepository;
import com.mycompany.playmatch.service.EncuestaService;
import com.mycompany.playmatch.service.dto.EncuestaDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.Encuesta}.
 */
@RestController
@RequestMapping("/api/encuestas")
public class EncuestaResource {

    private static final Logger LOG = LoggerFactory.getLogger(EncuestaResource.class);

    private static final String ENTITY_NAME = "encuesta";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final EncuestaService encuestaService;

    private final EncuestaRepository encuestaRepository;

    public EncuestaResource(EncuestaService encuestaService, EncuestaRepository encuestaRepository) {
        this.encuestaService = encuestaService;
        this.encuestaRepository = encuestaRepository;
    }

    /**
     * {@code POST  /encuestas} : Create a new encuesta.
     *
     * @param encuestaDTO the encuestaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encuestaDTO, or with status {@code 400 (Bad Request)} if the encuesta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EncuestaDTO> createEncuesta(@Valid @RequestBody EncuestaDTO encuestaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Encuesta : {}", encuestaDTO);
        if (encuestaDTO.getId() != null) {
            throw new BadRequestAlertException("A new encuesta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        encuestaDTO = encuestaService.save(encuestaDTO);
        return ResponseEntity.created(new URI("/api/encuestas/" + encuestaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, encuestaDTO.getId()))
            .body(encuestaDTO);
    }

    /**
     * {@code PUT  /encuestas/:id} : Updates an existing encuesta.
     *
     * @param id the id of the encuestaDTO to save.
     * @param encuestaDTO the encuestaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encuestaDTO,
     * or with status {@code 400 (Bad Request)} if the encuestaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encuestaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EncuestaDTO> updateEncuesta(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EncuestaDTO encuestaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Encuesta : {}, {}", id, encuestaDTO);
        if (encuestaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encuestaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encuestaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        encuestaDTO = encuestaService.update(encuestaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, encuestaDTO.getId()))
            .body(encuestaDTO);
    }

    /**
     * {@code PATCH  /encuestas/:id} : Partial updates given fields of an existing encuesta, field will ignore if it is null
     *
     * @param id the id of the encuestaDTO to save.
     * @param encuestaDTO the encuestaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encuestaDTO,
     * or with status {@code 400 (Bad Request)} if the encuestaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the encuestaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the encuestaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EncuestaDTO> partialUpdateEncuesta(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EncuestaDTO encuestaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Encuesta partially : {}, {}", id, encuestaDTO);
        if (encuestaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encuestaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encuestaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EncuestaDTO> result = encuestaService.partialUpdate(encuestaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, encuestaDTO.getId())
        );
    }

    /**
     * {@code GET  /encuestas} : get all the Encuestas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Encuestas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EncuestaDTO>> getAllEncuestas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Encuestas");
        Page<EncuestaDTO> page;
        if (eagerload) {
            page = encuestaService.findAllWithEagerRelationships(pageable);
        } else {
            page = encuestaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encuestas/:id} : get the "id" encuesta.
     *
     * @param id the id of the encuestaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encuestaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EncuestaDTO> getEncuesta(@PathVariable("id") String id) {
        LOG.debug("REST request to get Encuesta : {}", id);
        Optional<EncuestaDTO> encuestaDTO = encuestaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encuestaDTO);
    }

    /**
     * {@code DELETE  /encuestas/:id} : delete the "id" encuesta.
     *
     * @param id the id of the encuestaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEncuesta(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Encuesta : {}", id);
        encuestaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
