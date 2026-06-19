package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Encuesta;
import com.mycompany.playmatch.repository.EncuestaRepository;
import com.mycompany.playmatch.service.EncuestaService;
import com.mycompany.playmatch.service.dto.EncuestaDTO;
import com.mycompany.playmatch.service.mapper.EncuestaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Encuesta}.
 */
@Service
public class EncuestaServiceImpl implements EncuestaService {

    private static final Logger LOG = LoggerFactory.getLogger(EncuestaServiceImpl.class);

    private final EncuestaRepository encuestaRepository;

    private final EncuestaMapper encuestaMapper;

    public EncuestaServiceImpl(EncuestaRepository encuestaRepository, EncuestaMapper encuestaMapper) {
        this.encuestaRepository = encuestaRepository;
        this.encuestaMapper = encuestaMapper;
    }

    @Override
    public EncuestaDTO save(EncuestaDTO encuestaDTO) {
        LOG.debug("Request to save Encuesta : {}", encuestaDTO);
        Encuesta encuesta = encuestaMapper.toEntity(encuestaDTO);
        encuesta = encuestaRepository.save(encuesta);
        return encuestaMapper.toDto(encuesta);
    }

    @Override
    public EncuestaDTO update(EncuestaDTO encuestaDTO) {
        LOG.debug("Request to update Encuesta : {}", encuestaDTO);
        Encuesta encuesta = encuestaMapper.toEntity(encuestaDTO);
        encuesta = encuestaRepository.save(encuesta);
        return encuestaMapper.toDto(encuesta);
    }

    @Override
    public Optional<EncuestaDTO> partialUpdate(EncuestaDTO encuestaDTO) {
        LOG.debug("Request to partially update Encuesta : {}", encuestaDTO);

        return encuestaRepository
            .findById(encuestaDTO.getId())
            .map(existingEncuesta -> {
                encuestaMapper.partialUpdate(existingEncuesta, encuestaDTO);

                return existingEncuesta;
            })
            .map(encuestaRepository::save)
            .map(encuestaMapper::toDto);
    }

    @Override
    public Page<EncuestaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Encuestas");
        return encuestaRepository.findAll(pageable).map(encuestaMapper::toDto);
    }

    public Page<EncuestaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return encuestaRepository.findAllWithEagerRelationships(pageable).map(encuestaMapper::toDto);
    }

    @Override
    public Optional<EncuestaDTO> findOne(String id) {
        LOG.debug("Request to get Encuesta : {}", id);
        return encuestaRepository.findOneWithEagerRelationships(id).map(encuestaMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Encuesta : {}", id);
        encuestaRepository.deleteById(id);
    }
}
