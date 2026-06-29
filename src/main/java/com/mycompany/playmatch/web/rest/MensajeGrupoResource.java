package com.mycompany.playmatch.web.rest;

import com.mycompany.playmatch.repository.MensajeGrupoRepository;
import com.mycompany.playmatch.service.MensajeGrupoService;
import com.mycompany.playmatch.service.dto.MensajeGrupoDTO;
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
 * REST controller for managing {@link com.mycompany.playmatch.domain.MensajeGrupo}.
 */
@RestController
@RequestMapping("/api/mensaje-grupos")
public class MensajeGrupoResource {

    private static final Logger LOG = LoggerFactory.getLogger(MensajeGrupoResource.class);

    private static final String ENTITY_NAME = "mensajeGrupo";

    @Value("${jhipster.clientApp.name:playmatch}")
    private String applicationName;

    private final MensajeGrupoService mensajeGrupoService;

    private final MensajeGrupoRepository mensajeGrupoRepository;

    public MensajeGrupoResource(MensajeGrupoService mensajeGrupoService, MensajeGrupoRepository mensajeGrupoRepository) {
        this.mensajeGrupoService = mensajeGrupoService;
        this.mensajeGrupoRepository = mensajeGrupoRepository;
    }

    /**
     * {@code POST  /mensaje-grupos} : Create a new mensajeGrupo.
     *
     * @param mensajeGrupoDTO the mensajeGrupoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensajeGrupoDTO, or with status {@code 400 (Bad Request)} if the mensajeGrupo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<MensajeGrupoDTO> createMensajeGrupo(@Valid @RequestBody MensajeGrupoDTO mensajeGrupoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MensajeGrupo : {}", mensajeGrupoDTO);
        if (mensajeGrupoDTO.getId() != null) {
            throw new BadRequestAlertException("A new mensajeGrupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mensajeGrupoDTO = mensajeGrupoService.save(mensajeGrupoDTO);
        return ResponseEntity.created(new URI("/api/mensaje-grupos/" + mensajeGrupoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mensajeGrupoDTO.getId()))
            .body(mensajeGrupoDTO);
    }

    /**
     * {@code PUT  /mensaje-grupos/:id} : Updates an existing mensajeGrupo.
     *
     * @param id the id of the mensajeGrupoDTO to save.
     * @param mensajeGrupoDTO the mensajeGrupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajeGrupoDTO,
     * or with status {@code 400 (Bad Request)} if the mensajeGrupoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensajeGrupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<MensajeGrupoDTO> updateMensajeGrupo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody MensajeGrupoDTO mensajeGrupoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MensajeGrupo : {}, {}", id, mensajeGrupoDTO);
        if (mensajeGrupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensajeGrupoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensajeGrupoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mensajeGrupoDTO = mensajeGrupoService.update(mensajeGrupoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mensajeGrupoDTO.getId()))
            .body(mensajeGrupoDTO);
    }

    /**
     * {@code PATCH  /mensaje-grupos/:id} : Partial updates given fields of an existing mensajeGrupo, field will ignore if it is null
     *
     * @param id the id of the mensajeGrupoDTO to save.
     * @param mensajeGrupoDTO the mensajeGrupoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajeGrupoDTO,
     * or with status {@code 400 (Bad Request)} if the mensajeGrupoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mensajeGrupoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mensajeGrupoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MensajeGrupoDTO> partialUpdateMensajeGrupo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody MensajeGrupoDTO mensajeGrupoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MensajeGrupo partially : {}, {}", id, mensajeGrupoDTO);
        if (mensajeGrupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensajeGrupoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensajeGrupoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MensajeGrupoDTO> result = mensajeGrupoService.partialUpdate(mensajeGrupoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mensajeGrupoDTO.getId())
        );
    }

    /**
     * {@code GET  /mensaje-grupos} : get all the Mensaje Grupos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Mensaje Grupos in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<List<MensajeGrupoDTO>> getAllMensajeGrupos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of MensajeGrupos");
        Page<MensajeGrupoDTO> page;
        if (eagerload) {
            page = mensajeGrupoService.findAllWithEagerRelationships(pageable);
        } else {
            page = mensajeGrupoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensaje-grupos/:id} : get the "id" mensajeGrupo.
     *
     * @param id the id of the mensajeGrupoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensajeGrupoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\") or hasAuthority(\"" + AuthoritiesConstants.ORGANIZADOR + "\") or hasAuthority(\"" + AuthoritiesConstants.PARTICIPANTE + "\")")
    public ResponseEntity<MensajeGrupoDTO> getMensajeGrupo(@PathVariable("id") String id) {
        LOG.debug("REST request to get MensajeGrupo : {}", id);
        Optional<MensajeGrupoDTO> mensajeGrupoDTO = mensajeGrupoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensajeGrupoDTO);
    }

    /**
     * {@code DELETE  /mensaje-grupos/:id} : delete the "id" mensajeGrupo.
     *
     * @param id the id of the mensajeGrupoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\""+ AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMensajeGrupo(@PathVariable("id") String id) {
        LOG.debug("REST request to delete MensajeGrupo : {}", id);
        mensajeGrupoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
            .build();
    }
}
