package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.repository.GrupoRepository;
import com.mycompany.playmatch.service.GrupoService;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.mapper.GrupoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Grupo}.
 */
@Service
public class GrupoServiceImpl implements GrupoService {

    private static final Logger LOG = LoggerFactory.getLogger(GrupoServiceImpl.class);

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;

    public GrupoServiceImpl(GrupoRepository grupoRepository, GrupoMapper grupoMapper) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
    }

    @Override
    public GrupoDTO save(GrupoDTO grupoDTO) {
        LOG.debug("Request to save Grupo : {}", grupoDTO);
        Grupo grupo = grupoMapper.toEntity(grupoDTO);
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toDto(grupo);
    }

    @Override
    public GrupoDTO update(GrupoDTO grupoDTO) {
        LOG.debug("Request to update Grupo : {}", grupoDTO);
        Grupo grupo = grupoMapper.toEntity(grupoDTO);
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toDto(grupo);
    }

    @Override
    public Optional<GrupoDTO> partialUpdate(GrupoDTO grupoDTO) {
        LOG.debug("Request to partially update Grupo : {}", grupoDTO);

        return grupoRepository
            .findById(grupoDTO.getId())
            .map(existingGrupo -> {
                grupoMapper.partialUpdate(existingGrupo, grupoDTO);

                return existingGrupo;
            })
            .map(grupoRepository::save)
            .map(grupoMapper::toDto);
    }

    @Override
    public Page<GrupoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Grupos");
        return grupoRepository.findAll(pageable).map(grupoMapper::toDto);
    }

    @Override
    public Optional<GrupoDTO> findOne(String id) {
        LOG.debug("Request to get Grupo : {}", id);
        return grupoRepository.findById(id).map(grupoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Grupo : {}", id);
        grupoRepository.deleteById(id);
    }
}
