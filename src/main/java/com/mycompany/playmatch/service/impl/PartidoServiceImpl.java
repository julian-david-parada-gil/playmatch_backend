package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Partido;
import com.mycompany.playmatch.repository.PartidoRepository;
import com.mycompany.playmatch.service.PartidoService;
import com.mycompany.playmatch.service.dto.PartidoDTO;
import com.mycompany.playmatch.service.mapper.PartidoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Partido}.
 */
@Service
public class PartidoServiceImpl implements PartidoService {

    private static final Logger LOG = LoggerFactory.getLogger(PartidoServiceImpl.class);

    private final PartidoRepository partidoRepository;

    private final PartidoMapper partidoMapper;

    public PartidoServiceImpl(PartidoRepository partidoRepository, PartidoMapper partidoMapper) {
        this.partidoRepository = partidoRepository;
        this.partidoMapper = partidoMapper;
    }

    @Override
    public PartidoDTO save(PartidoDTO partidoDTO) {
        LOG.debug("Request to save Partido : {}", partidoDTO);
        Partido partido = partidoMapper.toEntity(partidoDTO);
        partido = partidoRepository.save(partido);
        return partidoMapper.toDto(partido);
    }

    @Override
    public PartidoDTO update(PartidoDTO partidoDTO) {
        LOG.debug("Request to update Partido : {}", partidoDTO);
        Partido partido = partidoMapper.toEntity(partidoDTO);
        partido = partidoRepository.save(partido);
        return partidoMapper.toDto(partido);
    }

    @Override
    public Optional<PartidoDTO> partialUpdate(PartidoDTO partidoDTO) {
        LOG.debug("Request to partially update Partido : {}", partidoDTO);

        return partidoRepository
            .findById(partidoDTO.getId())
            .map(existingPartido -> {
                partidoMapper.partialUpdate(existingPartido, partidoDTO);

                return existingPartido;
            })
            .map(partidoRepository::save)
            .map(partidoMapper::toDto);
    }

    @Override
    public Page<PartidoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Partidos");
        return partidoRepository.findAll(pageable).map(partidoMapper::toDto);
    }

    public Page<PartidoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return partidoRepository.findAllWithEagerRelationships(pageable).map(partidoMapper::toDto);
    }

    @Override
    public Optional<PartidoDTO> findOne(String id) {
        LOG.debug("Request to get Partido : {}", id);
        return partidoRepository.findOneWithEagerRelationships(id).map(partidoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Partido : {}", id);
        partidoRepository.deleteById(id);
    }
}
