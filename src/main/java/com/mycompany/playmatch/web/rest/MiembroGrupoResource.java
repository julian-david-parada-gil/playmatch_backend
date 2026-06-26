package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.MiembroGrupoRepository;
import com.mycompany.playmatch.service.MiembroGrupoService;
import com.mycompany.playmatch.service.dto.MiembroGrupoDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.MiembroGrupo}.
 */
@RestController
@RequestMapping("/api/miembro-grupos")
public class MiembroGrupoResource {

    private static final Logger LOG = LoggerFactory.getLogger(MiembroGrupoResource.class);

    private static final String ENTITY_NAME = "miembroGrupo";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final MiembroGrupoService miembroGrupoService;

    private final MiembroGrupoRepository miembroGrupoRepository;

    public MiembroGrupoResource(MiembroGrupoService miembroGrupoService, MiembroGrupoRepository miembroGrupoRepository) {
        this.miembroGrupoService = miembroGrupoService;
        this.miembroGrupoRepository = miembroGrupoRepository;
    }

    /**
     * {@code POST  /miembro-grupos} : Create a new miembroGrupo.
     *
     * @param miembroGrupoDTO the miembroGrupoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new miembroGrupoDTO, or with status {@code 400 (Bad Request)} if the miembroGrupo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<MiembroGrupoDTO> createMiembroGrupo(@Valid @RequestBody MiembroGrupoDTO miembroGrupoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MiembroGrupo : {}", miembroGrupoDTO);
        if (miembroGrupoDTO.getId() != null) {
            throw new BadRequestAlertException("A new miembroGrupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        miembroGrupoDTO = miembroGrupoService.save(miembroGrupoDTO);
        return ResponseEntity.created(new URI("/api/miembro-grupos/" + miembroGrupoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, miembroGrupoDTO.getId()))
            .body(miembroGrupoDTO);
    }

    /**
     * {@code PUT  /miembro-grupos/:id} : Updates an existing miembroGrupo.
     *
     * @param id the id of the miembroGrupoDTO to save.
     * @param miembroGrupoDTO the miembroGrupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated miembroGrupoDTO,
     * or with status {@code 400 (Bad Request)} if the miembroGrupoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the miembroGrupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<MiembroGrupoDTO> updateMiembroGrupo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MiembroGrupoDTO miembroGrupoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MiembroGrupo : {}, {}", id, miembroGrupoDTO);
        if (miembroGrupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, miembroGrupoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!miembroGrupoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        miembroGrupoDTO = miembroGrupoService.update(miembroGrupoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, miembroGrupoDTO.getId()))
            .body(miembroGrupoDTO);
    }

    /**
     * {@code PATCH  /miembro-grupos/:id} : Partial updates given fields of an existing miembroGrupo, field will ignore if it is null
     *
     * @param id the id of the miembroGrupoDTO to save.
     * @param miembroGrupoDTO the miembroGrupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated miembroGrupoDTO,
     * or with status {@code 400 (Bad Request)} if the miembroGrupoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the miembroGrupoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the miembroGrupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MiembroGrupoDTO> partialUpdateMiembroGrupo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MiembroGrupoDTO miembroGrupoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MiembroGrupo partially : {}, {}", id, miembroGrupoDTO);
        if (miembroGrupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, miembroGrupoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!miembroGrupoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MiembroGrupoDTO> result = miembroGrupoService.partialUpdate(miembroGrupoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, miembroGrupoDTO.getId())
        );
    }

    /**
     * {@code GET  /miembro-grupos} : get all the Miembro Grupos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Miembro Grupos in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<List<MiembroGrupoDTO>> getAllMiembroGrupos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of MiembroGrupos");
        Page<MiembroGrupoDTO> page;
        if (eagerload) {
            page = miembroGrupoService.findAllWithEagerRelationships(pageable);
        } else {
            page = miembroGrupoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /miembro-grupos/:id} : get the "id" miembroGrupo.
     *
     * @param id the id of the miembroGrupoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the miembroGrupoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<MiembroGrupoDTO> getMiembroGrupo(@PathVariable("id") String id) {
        LOG.debug("REST request to get MiembroGrupo : {}", id);
        Optional<MiembroGrupoDTO> miembroGrupoDTO = miembroGrupoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(miembroGrupoDTO);
    }

    /**
     * {@code DELETE  /miembro-grupos/:id} : delete the "id" miembroGrupo.
     *
     * @param id the id of the miembroGrupoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\")")
    public ResponseEntity<Void> deleteMiembroGrupo(@PathVariable("id") String id) {
        LOG.debug("REST request to delete MiembroGrupo : {}", id);
        miembroGrupoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
