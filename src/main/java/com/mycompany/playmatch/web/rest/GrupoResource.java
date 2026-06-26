package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.GrupoRepository;
import com.mycompany.playmatch.service.GrupoService;
import com.mycompany.playmatch.service.dto.GrupoDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.Grupo}.
 */
@RestController
@RequestMapping("/api/grupos")
public class GrupoResource {

    private static final Logger LOG = LoggerFactory.getLogger(GrupoResource.class);

    private static final String ENTITY_NAME = "grupo";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final GrupoService grupoService;

    private final GrupoRepository grupoRepository;

    public GrupoResource(GrupoService grupoService, GrupoRepository grupoRepository) {
        this.grupoService = grupoService;
        this.grupoRepository = grupoRepository;
    }

    /**
     * {@code POST  /grupos} : Create a new grupo.
     *
     * @param grupoDTO the grupoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoDTO, or with status {@code 400 (Bad Request)} if the grupo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<GrupoDTO> createGrupo(@Valid @RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Grupo : {}", grupoDTO);
        if (grupoDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoDTO = grupoService.save(grupoDTO);
        return ResponseEntity.created(new URI("/api/grupos/" + grupoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, grupoDTO.getId()))
            .body(grupoDTO);
    }

    /**
     * {@code PUT  /grupos/:id} : Updates an existing grupo.
     *
     * @param id the id of the grupoDTO to save.
     * @param grupoDTO the grupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoDTO,
     * or with status {@code 400 (Bad Request)} if the grupoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<GrupoDTO> updateGrupo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GrupoDTO grupoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Grupo : {}, {}", id, grupoDTO);
        if (grupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoDTO = grupoService.update(grupoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoDTO.getId()))
            .body(grupoDTO);
    }

    /**
     * {@code PATCH  /grupos/:id} : Partial updates given fields of an existing grupo, field will ignore if it is null
     *
     * @param id the id of the grupoDTO to save.
     * @param grupoDTO the grupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoDTO,
     * or with status {@code 400 (Bad Request)} if the grupoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the grupoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<GrupoDTO> partialUpdateGrupo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GrupoDTO grupoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Grupo partially : {}, {}", id, grupoDTO);
        if (grupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoDTO> result = grupoService.partialUpdate(grupoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoDTO.getId())
        );
    }

    /**
     * {@code GET  /grupos} : get all the Grupos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Grupos in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<List<GrupoDTO>> getAllGrupos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Grupos");
        Page<GrupoDTO> page = grupoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupos/:id} : get the "id" grupo.
     *
     * @param id the id of the grupoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<GrupoDTO> getGrupo(@PathVariable("id") String id) {
        LOG.debug("REST request to get Grupo : {}", id);
        Optional<GrupoDTO> grupoDTO = grupoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoDTO);
    }

    /**
     * {@code DELETE  /grupos/:id} : delete the "id" grupo.
     *
     * @param id the id of the grupoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteGrupo(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Grupo : {}", id);
        grupoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
