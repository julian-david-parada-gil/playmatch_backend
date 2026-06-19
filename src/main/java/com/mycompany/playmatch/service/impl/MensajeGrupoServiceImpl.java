package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.MensajeGrupo;
import com.mycompany.playmatch.repository.MensajeGrupoRepository;
import com.mycompany.playmatch.service.MensajeGrupoService;
import com.mycompany.playmatch.service.dto.MensajeGrupoDTO;
import com.mycompany.playmatch.service.mapper.MensajeGrupoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.MensajeGrupo}.
 */
@Service
public class MensajeGrupoServiceImpl implements MensajeGrupoService {

    private static final Logger LOG = LoggerFactory.getLogger(MensajeGrupoServiceImpl.class);

    private final MensajeGrupoRepository mensajeGrupoRepository;

    private final MensajeGrupoMapper mensajeGrupoMapper;

    public MensajeGrupoServiceImpl(MensajeGrupoRepository mensajeGrupoRepository, MensajeGrupoMapper mensajeGrupoMapper) {
        this.mensajeGrupoRepository = mensajeGrupoRepository;
        this.mensajeGrupoMapper = mensajeGrupoMapper;
    }

    @Override
    public MensajeGrupoDTO save(MensajeGrupoDTO mensajeGrupoDTO) {
        LOG.debug("Request to save MensajeGrupo : {}", mensajeGrupoDTO);
        MensajeGrupo mensajeGrupo = mensajeGrupoMapper.toEntity(mensajeGrupoDTO);
        mensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);
        return mensajeGrupoMapper.toDto(mensajeGrupo);
    }

    @Override
    public MensajeGrupoDTO update(MensajeGrupoDTO mensajeGrupoDTO) {
        LOG.debug("Request to update MensajeGrupo : {}", mensajeGrupoDTO);
        MensajeGrupo mensajeGrupo = mensajeGrupoMapper.toEntity(mensajeGrupoDTO);
        mensajeGrupo = mensajeGrupoRepository.save(mensajeGrupo);
        return mensajeGrupoMapper.toDto(mensajeGrupo);
    }

    @Override
    public Optional<MensajeGrupoDTO> partialUpdate(MensajeGrupoDTO mensajeGrupoDTO) {
        LOG.debug("Request to partially update MensajeGrupo : {}", mensajeGrupoDTO);

        return mensajeGrupoRepository
            .findById(mensajeGrupoDTO.getId())
            .map(existingMensajeGrupo -> {
                mensajeGrupoMapper.partialUpdate(existingMensajeGrupo, mensajeGrupoDTO);

                return existingMensajeGrupo;
            })
            .map(mensajeGrupoRepository::save)
            .map(mensajeGrupoMapper::toDto);
    }

    @Override
    public Page<MensajeGrupoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MensajeGrupos");
        return mensajeGrupoRepository.findAll(pageable).map(mensajeGrupoMapper::toDto);
    }

    public Page<MensajeGrupoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mensajeGrupoRepository.findAllWithEagerRelationships(pageable).map(mensajeGrupoMapper::toDto);
    }

    @Override
    public Optional<MensajeGrupoDTO> findOne(String id) {
        LOG.debug("Request to get MensajeGrupo : {}", id);
        return mensajeGrupoRepository.findOneWithEagerRelationships(id).map(mensajeGrupoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete MensajeGrupo : {}", id);
        mensajeGrupoRepository.deleteById(id);
    }
}
