package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.TablaPosicion;
import com.mycompany.playmatch.repository.TablaPosicionRepository;
import com.mycompany.playmatch.service.TablaPosicionService;
import com.mycompany.playmatch.service.dto.TablaPosicionDTO;
import com.mycompany.playmatch.service.mapper.TablaPosicionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.TablaPosicion}.
 */
@Service
public class TablaPosicionServiceImpl implements TablaPosicionService {

    private static final Logger LOG = LoggerFactory.getLogger(TablaPosicionServiceImpl.class);

    private final TablaPosicionRepository tablaPosicionRepository;

    private final TablaPosicionMapper tablaPosicionMapper;

    public TablaPosicionServiceImpl(TablaPosicionRepository tablaPosicionRepository, TablaPosicionMapper tablaPosicionMapper) {
        this.tablaPosicionRepository = tablaPosicionRepository;
        this.tablaPosicionMapper = tablaPosicionMapper;
    }

    @Override
    public TablaPosicionDTO save(TablaPosicionDTO tablaPosicionDTO) {
        LOG.debug("Request to save TablaPosicion : {}", tablaPosicionDTO);
        TablaPosicion tablaPosicion = tablaPosicionMapper.toEntity(tablaPosicionDTO);
        tablaPosicion = tablaPosicionRepository.save(tablaPosicion);
        return tablaPosicionMapper.toDto(tablaPosicion);
    }

    @Override
    public TablaPosicionDTO update(TablaPosicionDTO tablaPosicionDTO) {
        LOG.debug("Request to update TablaPosicion : {}", tablaPosicionDTO);
        TablaPosicion tablaPosicion = tablaPosicionMapper.toEntity(tablaPosicionDTO);
        tablaPosicion = tablaPosicionRepository.save(tablaPosicion);
        return tablaPosicionMapper.toDto(tablaPosicion);
    }

    @Override
    public Optional<TablaPosicionDTO> partialUpdate(TablaPosicionDTO tablaPosicionDTO) {
        LOG.debug("Request to partially update TablaPosicion : {}", tablaPosicionDTO);

        return tablaPosicionRepository
            .findById(tablaPosicionDTO.getId())
            .map(existingTablaPosicion -> {
                tablaPosicionMapper.partialUpdate(existingTablaPosicion, tablaPosicionDTO);

                return existingTablaPosicion;
            })
            .map(tablaPosicionRepository::save)
            .map(tablaPosicionMapper::toDto);
    }

    @Override
    public Page<TablaPosicionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TablaPosicions");
        return tablaPosicionRepository.findAll(pageable).map(tablaPosicionMapper::toDto);
    }

    public Page<TablaPosicionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tablaPosicionRepository.findAllWithEagerRelationships(pageable).map(tablaPosicionMapper::toDto);
    }

    @Override
    public Optional<TablaPosicionDTO> findOne(String id) {
        LOG.debug("Request to get TablaPosicion : {}", id);
        return tablaPosicionRepository.findOneWithEagerRelationships(id).map(tablaPosicionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete TablaPosicion : {}", id);
        tablaPosicionRepository.deleteById(id);
    }
}
