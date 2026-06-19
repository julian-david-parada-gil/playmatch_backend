package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Inscripcion;
import com.mycompany.playmatch.repository.InscripcionRepository;
import com.mycompany.playmatch.service.InscripcionService;
import com.mycompany.playmatch.service.dto.InscripcionDTO;
import com.mycompany.playmatch.service.mapper.InscripcionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Inscripcion}.
 */
@Service
public class InscripcionServiceImpl implements InscripcionService {

    private static final Logger LOG = LoggerFactory.getLogger(InscripcionServiceImpl.class);

    private final InscripcionRepository inscripcionRepository;

    private final InscripcionMapper inscripcionMapper;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository, InscripcionMapper inscripcionMapper) {
        this.inscripcionRepository = inscripcionRepository;
        this.inscripcionMapper = inscripcionMapper;
    }

    @Override
    public InscripcionDTO save(InscripcionDTO inscripcionDTO) {
        LOG.debug("Request to save Inscripcion : {}", inscripcionDTO);
        Inscripcion inscripcion = inscripcionMapper.toEntity(inscripcionDTO);
        inscripcion = inscripcionRepository.save(inscripcion);
        return inscripcionMapper.toDto(inscripcion);
    }

    @Override
    public InscripcionDTO update(InscripcionDTO inscripcionDTO) {
        LOG.debug("Request to update Inscripcion : {}", inscripcionDTO);
        Inscripcion inscripcion = inscripcionMapper.toEntity(inscripcionDTO);
        inscripcion = inscripcionRepository.save(inscripcion);
        return inscripcionMapper.toDto(inscripcion);
    }

    @Override
    public Optional<InscripcionDTO> partialUpdate(InscripcionDTO inscripcionDTO) {
        LOG.debug("Request to partially update Inscripcion : {}", inscripcionDTO);

        return inscripcionRepository
            .findById(inscripcionDTO.getId())
            .map(existingInscripcion -> {
                inscripcionMapper.partialUpdate(existingInscripcion, inscripcionDTO);

                return existingInscripcion;
            })
            .map(inscripcionRepository::save)
            .map(inscripcionMapper::toDto);
    }

    @Override
    public Page<InscripcionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Inscripcions");
        return inscripcionRepository.findAll(pageable).map(inscripcionMapper::toDto);
    }

    public Page<InscripcionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return inscripcionRepository.findAllWithEagerRelationships(pageable).map(inscripcionMapper::toDto);
    }

    @Override
    public Optional<InscripcionDTO> findOne(String id) {
        LOG.debug("Request to get Inscripcion : {}", id);
        return inscripcionRepository.findOneWithEagerRelationships(id).map(inscripcionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Inscripcion : {}", id);
        inscripcionRepository.deleteById(id);
    }
}
