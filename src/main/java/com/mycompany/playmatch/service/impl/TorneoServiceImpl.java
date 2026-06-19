package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.repository.TorneoRepository;
import com.mycompany.playmatch.service.TorneoService;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import com.mycompany.playmatch.service.mapper.TorneoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Torneo}.
 */
@Service
public class TorneoServiceImpl implements TorneoService {

    private static final Logger LOG = LoggerFactory.getLogger(TorneoServiceImpl.class);

    private final TorneoRepository torneoRepository;

    private final TorneoMapper torneoMapper;

    public TorneoServiceImpl(TorneoRepository torneoRepository, TorneoMapper torneoMapper) {
        this.torneoRepository = torneoRepository;
        this.torneoMapper = torneoMapper;
    }

    @Override
    public TorneoDTO save(TorneoDTO torneoDTO) {
        LOG.debug("Request to save Torneo : {}", torneoDTO);
        Torneo torneo = torneoMapper.toEntity(torneoDTO);
        torneo = torneoRepository.save(torneo);
        return torneoMapper.toDto(torneo);
    }

    @Override
    public TorneoDTO update(TorneoDTO torneoDTO) {
        LOG.debug("Request to update Torneo : {}", torneoDTO);
        Torneo torneo = torneoMapper.toEntity(torneoDTO);
        torneo = torneoRepository.save(torneo);
        return torneoMapper.toDto(torneo);
    }

    @Override
    public Optional<TorneoDTO> partialUpdate(TorneoDTO torneoDTO) {
        LOG.debug("Request to partially update Torneo : {}", torneoDTO);

        return torneoRepository
            .findById(torneoDTO.getId())
            .map(existingTorneo -> {
                torneoMapper.partialUpdate(existingTorneo, torneoDTO);

                return existingTorneo;
            })
            .map(torneoRepository::save)
            .map(torneoMapper::toDto);
    }

    @Override
    public Page<TorneoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Torneos");
        return torneoRepository.findAll(pageable).map(torneoMapper::toDto);
    }

    public Page<TorneoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return torneoRepository.findAllWithEagerRelationships(pageable).map(torneoMapper::toDto);
    }

    @Override
    public Optional<TorneoDTO> findOne(String id) {
        LOG.debug("Request to get Torneo : {}", id);
        return torneoRepository.findOneWithEagerRelationships(id).map(torneoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Torneo : {}", id);
        torneoRepository.deleteById(id);
    }
}
