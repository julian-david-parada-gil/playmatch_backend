package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.MiembroGrupo;
import com.mycompany.playmatch.repository.MiembroGrupoRepository;
import com.mycompany.playmatch.service.MiembroGrupoService;
import com.mycompany.playmatch.service.dto.MiembroGrupoDTO;
import com.mycompany.playmatch.service.mapper.MiembroGrupoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.MiembroGrupo}.
 */
@Service
public class MiembroGrupoServiceImpl implements MiembroGrupoService {

    private static final Logger LOG = LoggerFactory.getLogger(MiembroGrupoServiceImpl.class);

    private final MiembroGrupoRepository miembroGrupoRepository;

    private final MiembroGrupoMapper miembroGrupoMapper;

    public MiembroGrupoServiceImpl(MiembroGrupoRepository miembroGrupoRepository, MiembroGrupoMapper miembroGrupoMapper) {
        this.miembroGrupoRepository = miembroGrupoRepository;
        this.miembroGrupoMapper = miembroGrupoMapper;
    }

    @Override
    public MiembroGrupoDTO save(MiembroGrupoDTO miembroGrupoDTO) {
        LOG.debug("Request to save MiembroGrupo : {}", miembroGrupoDTO);
        MiembroGrupo miembroGrupo = miembroGrupoMapper.toEntity(miembroGrupoDTO);
        miembroGrupo = miembroGrupoRepository.save(miembroGrupo);
        return miembroGrupoMapper.toDto(miembroGrupo);
    }

    @Override
    public MiembroGrupoDTO update(MiembroGrupoDTO miembroGrupoDTO) {
        LOG.debug("Request to update MiembroGrupo : {}", miembroGrupoDTO);
        MiembroGrupo miembroGrupo = miembroGrupoMapper.toEntity(miembroGrupoDTO);
        miembroGrupo = miembroGrupoRepository.save(miembroGrupo);
        return miembroGrupoMapper.toDto(miembroGrupo);
    }

    @Override
    public Optional<MiembroGrupoDTO> partialUpdate(MiembroGrupoDTO miembroGrupoDTO) {
        LOG.debug("Request to partially update MiembroGrupo : {}", miembroGrupoDTO);

        return miembroGrupoRepository
            .findById(miembroGrupoDTO.getId())
            .map(existingMiembroGrupo -> {
                miembroGrupoMapper.partialUpdate(existingMiembroGrupo, miembroGrupoDTO);

                return existingMiembroGrupo;
            })
            .map(miembroGrupoRepository::save)
            .map(miembroGrupoMapper::toDto);
    }

    @Override
    public Page<MiembroGrupoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MiembroGrupos");
        return miembroGrupoRepository.findAll(pageable).map(miembroGrupoMapper::toDto);
    }

    public Page<MiembroGrupoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return miembroGrupoRepository.findAllWithEagerRelationships(pageable).map(miembroGrupoMapper::toDto);
    }

    @Override
    public Optional<MiembroGrupoDTO> findOne(String id) {
        LOG.debug("Request to get MiembroGrupo : {}", id);
        return miembroGrupoRepository.findOneWithEagerRelationships(id).map(miembroGrupoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete MiembroGrupo : {}", id);
        miembroGrupoRepository.deleteById(id);
    }
}
