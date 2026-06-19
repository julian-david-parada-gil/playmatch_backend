package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Calificacion;
import com.mycompany.playmatch.repository.CalificacionRepository;
import com.mycompany.playmatch.service.CalificacionService;
import com.mycompany.playmatch.service.dto.CalificacionDTO;
import com.mycompany.playmatch.service.mapper.CalificacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Calificacion}.
 */
@Service
public class CalificacionServiceImpl implements CalificacionService {

    private static final Logger LOG = LoggerFactory.getLogger(CalificacionServiceImpl.class);

    private final CalificacionRepository calificacionRepository;

    private final CalificacionMapper calificacionMapper;

    public CalificacionServiceImpl(CalificacionRepository calificacionRepository, CalificacionMapper calificacionMapper) {
        this.calificacionRepository = calificacionRepository;
        this.calificacionMapper = calificacionMapper;
    }

    @Override
    public CalificacionDTO save(CalificacionDTO calificacionDTO) {
        LOG.debug("Request to save Calificacion : {}", calificacionDTO);
        Calificacion calificacion = calificacionMapper.toEntity(calificacionDTO);
        calificacion = calificacionRepository.save(calificacion);
        return calificacionMapper.toDto(calificacion);
    }

    @Override
    public CalificacionDTO update(CalificacionDTO calificacionDTO) {
        LOG.debug("Request to update Calificacion : {}", calificacionDTO);
        Calificacion calificacion = calificacionMapper.toEntity(calificacionDTO);
        calificacion = calificacionRepository.save(calificacion);
        return calificacionMapper.toDto(calificacion);
    }

    @Override
    public Optional<CalificacionDTO> partialUpdate(CalificacionDTO calificacionDTO) {
        LOG.debug("Request to partially update Calificacion : {}", calificacionDTO);

        return calificacionRepository
            .findById(calificacionDTO.getId())
            .map(existingCalificacion -> {
                calificacionMapper.partialUpdate(existingCalificacion, calificacionDTO);

                return existingCalificacion;
            })
            .map(calificacionRepository::save)
            .map(calificacionMapper::toDto);
    }

    @Override
    public Page<CalificacionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Calificacions");
        return calificacionRepository.findAll(pageable).map(calificacionMapper::toDto);
    }

    public Page<CalificacionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return calificacionRepository.findAllWithEagerRelationships(pageable).map(calificacionMapper::toDto);
    }

    @Override
    public Optional<CalificacionDTO> findOne(String id) {
        LOG.debug("Request to get Calificacion : {}", id);
        return calificacionRepository.findOneWithEagerRelationships(id).map(calificacionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Calificacion : {}", id);
        calificacionRepository.deleteById(id);
    }
}
