package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Noticia;
import com.mycompany.playmatch.repository.NoticiaRepository;
import com.mycompany.playmatch.service.NoticiaService;
import com.mycompany.playmatch.service.dto.NoticiaDTO;
import com.mycompany.playmatch.service.mapper.NoticiaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Noticia}.
 */
@Service
public class NoticiaServiceImpl implements NoticiaService {

    private static final Logger LOG = LoggerFactory.getLogger(NoticiaServiceImpl.class);

    private final NoticiaRepository noticiaRepository;

    private final NoticiaMapper noticiaMapper;

    public NoticiaServiceImpl(NoticiaRepository noticiaRepository, NoticiaMapper noticiaMapper) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaMapper = noticiaMapper;
    }

    @Override
    public NoticiaDTO save(NoticiaDTO noticiaDTO) {
        LOG.debug("Request to save Noticia : {}", noticiaDTO);
        Noticia noticia = noticiaMapper.toEntity(noticiaDTO);
        noticia = noticiaRepository.save(noticia);
        return noticiaMapper.toDto(noticia);
    }

    @Override
    public NoticiaDTO update(NoticiaDTO noticiaDTO) {
        LOG.debug("Request to update Noticia : {}", noticiaDTO);
        Noticia noticia = noticiaMapper.toEntity(noticiaDTO);
        noticia = noticiaRepository.save(noticia);
        return noticiaMapper.toDto(noticia);
    }

    @Override
    public Optional<NoticiaDTO> partialUpdate(NoticiaDTO noticiaDTO) {
        LOG.debug("Request to partially update Noticia : {}", noticiaDTO);

        return noticiaRepository
            .findById(noticiaDTO.getId())
            .map(existingNoticia -> {
                noticiaMapper.partialUpdate(existingNoticia, noticiaDTO);

                return existingNoticia;
            })
            .map(noticiaRepository::save)
            .map(noticiaMapper::toDto);
    }

    @Override
    public Page<NoticiaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Noticias");
        return noticiaRepository.findAll(pageable).map(noticiaMapper::toDto);
    }

    @Override
    public Optional<NoticiaDTO> findOne(String id) {
        LOG.debug("Request to get Noticia : {}", id);
        return noticiaRepository.findById(id).map(noticiaMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Noticia : {}", id);
        noticiaRepository.deleteById(id);
    }
}
