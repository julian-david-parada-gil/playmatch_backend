package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Convocatoria;
import com.mycompany.playmatch.repository.ConvocatoriaRepository;
import com.mycompany.playmatch.service.ConvocatoriaService;
import com.mycompany.playmatch.service.dto.ConvocatoriaDTO;
import com.mycompany.playmatch.service.mapper.ConvocatoriaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Convocatoria}.
 */
@Service
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

    private static final Logger LOG = LoggerFactory.getLogger(ConvocatoriaServiceImpl.class);

    private final ConvocatoriaRepository convocatoriaRepository;

    private final ConvocatoriaMapper convocatoriaMapper;

    public ConvocatoriaServiceImpl(ConvocatoriaRepository convocatoriaRepository, ConvocatoriaMapper convocatoriaMapper) {
        this.convocatoriaRepository = convocatoriaRepository;
        this.convocatoriaMapper = convocatoriaMapper;
    }

    @Override
    public ConvocatoriaDTO save(ConvocatoriaDTO convocatoriaDTO) {
        LOG.debug("Request to save Convocatoria : {}", convocatoriaDTO);
        Convocatoria convocatoria = convocatoriaMapper.toEntity(convocatoriaDTO);
        convocatoria = convocatoriaRepository.save(convocatoria);
        return convocatoriaMapper.toDto(convocatoria);
    }

    @Override
    public ConvocatoriaDTO update(ConvocatoriaDTO convocatoriaDTO) {
        LOG.debug("Request to update Convocatoria : {}", convocatoriaDTO);
        Convocatoria convocatoria = convocatoriaMapper.toEntity(convocatoriaDTO);
        convocatoria = convocatoriaRepository.save(convocatoria);
        return convocatoriaMapper.toDto(convocatoria);
    }

    @Override
    public Optional<ConvocatoriaDTO> partialUpdate(ConvocatoriaDTO convocatoriaDTO) {
        LOG.debug("Request to partially update Convocatoria : {}", convocatoriaDTO);

        return convocatoriaRepository
            .findById(convocatoriaDTO.getId())
            .map(existingConvocatoria -> {
                convocatoriaMapper.partialUpdate(existingConvocatoria, convocatoriaDTO);

                return existingConvocatoria;
            })
            .map(convocatoriaRepository::save)
            .map(convocatoriaMapper::toDto);
    }

    @Override
    public Page<ConvocatoriaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Convocatorias");
        return convocatoriaRepository.findAll(pageable).map(convocatoriaMapper::toDto);
    }

    public Page<ConvocatoriaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return convocatoriaRepository.findAllWithEagerRelationships(pageable).map(convocatoriaMapper::toDto);
    }

    @Override
    public Optional<ConvocatoriaDTO> findOne(String id) {
        LOG.debug("Request to get Convocatoria : {}", id);
        return convocatoriaRepository.findOneWithEagerRelationships(id).map(convocatoriaMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Convocatoria : {}", id);
        convocatoriaRepository.deleteById(id);
    }
}
