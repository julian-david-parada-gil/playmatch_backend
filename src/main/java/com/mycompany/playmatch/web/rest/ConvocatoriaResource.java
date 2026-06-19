package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.ConvocatoriaRepository;
import com.mycompany.playmatch.service.ConvocatoriaService;
import com.mycompany.playmatch.service.dto.ConvocatoriaDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.Convocatoria}.
 */
@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConvocatoriaResource.class);

    private static final String ENTITY_NAME = "convocatoria";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final ConvocatoriaService convocatoriaService;

    private final ConvocatoriaRepository convocatoriaRepository;

    public ConvocatoriaResource(ConvocatoriaService convocatoriaService, ConvocatoriaRepository convocatoriaRepository) {
        this.convocatoriaService = convocatoriaService;
        this.convocatoriaRepository = convocatoriaRepository;
    }

    /**
     * {@code POST  /convocatorias} : Create a new convocatoria.
     *
     * @param convocatoriaDTO the convocatoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new convocatoriaDTO, or with status {@code 400 (Bad Request)} if the convocatoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConvocatoriaDTO> createConvocatoria(@Valid @RequestBody ConvocatoriaDTO convocatoriaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Convocatoria : {}", convocatoriaDTO);
        if (convocatoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new convocatoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        convocatoriaDTO = convocatoriaService.save(convocatoriaDTO);
        return ResponseEntity.created(new URI("/api/convocatorias/" + convocatoriaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, convocatoriaDTO.getId()))
            .body(convocatoriaDTO);
    }

    /**
     * {@code PUT  /convocatorias/:id} : Updates an existing convocatoria.
     *
     * @param id the id of the convocatoriaDTO to save.
     * @param convocatoriaDTO the convocatoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convocatoriaDTO,
     * or with status {@code 400 (Bad Request)} if the convocatoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the convocatoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConvocatoriaDTO> updateConvocatoria(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ConvocatoriaDTO convocatoriaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Convocatoria : {}, {}", id, convocatoriaDTO);
        if (convocatoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, convocatoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!convocatoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        convocatoriaDTO = convocatoriaService.update(convocatoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, convocatoriaDTO.getId()))
            .body(convocatoriaDTO);
    }

    /**
     * {@code PATCH  /convocatorias/:id} : Partial updates given fields of an existing convocatoria, field will ignore if it is null
     *
     * @param id the id of the convocatoriaDTO to save.
     * @param convocatoriaDTO the convocatoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convocatoriaDTO,
     * or with status {@code 400 (Bad Request)} if the convocatoriaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the convocatoriaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the convocatoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConvocatoriaDTO> partialUpdateConvocatoria(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ConvocatoriaDTO convocatoriaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Convocatoria partially : {}, {}", id, convocatoriaDTO);
        if (convocatoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, convocatoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!convocatoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConvocatoriaDTO> result = convocatoriaService.partialUpdate(convocatoriaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, convocatoriaDTO.getId())
        );
    }

    /**
     * {@code GET  /convocatorias} : get all the Convocatorias.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Convocatorias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConvocatoriaDTO>> getAllConvocatorias(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Convocatorias");
        Page<ConvocatoriaDTO> page;
        if (eagerload) {
            page = convocatoriaService.findAllWithEagerRelationships(pageable);
        } else {
            page = convocatoriaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /convocatorias/:id} : get the "id" convocatoria.
     *
     * @param id the id of the convocatoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the convocatoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConvocatoriaDTO> getConvocatoria(@PathVariable("id") String id) {
        LOG.debug("REST request to get Convocatoria : {}", id);
        Optional<ConvocatoriaDTO> convocatoriaDTO = convocatoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(convocatoriaDTO);
    }

    /**
     * {@code DELETE  /convocatorias/:id} : delete the "id" convocatoria.
     *
     * @param id the id of the convocatoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConvocatoria(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Convocatoria : {}", id);
        convocatoriaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
