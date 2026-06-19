package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.Notificacion;
import com.mycompany.playmatch.repository.NotificacionRepository;
import com.mycompany.playmatch.service.NotificacionService;
import com.mycompany.playmatch.service.dto.NotificacionDTO;
import com.mycompany.playmatch.service.mapper.NotificacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.Notificacion}.
 */
@Service
public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository notificacionRepository;

    private final NotificacionMapper notificacionMapper;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    @Override
    public NotificacionDTO save(NotificacionDTO notificacionDTO) {
        LOG.debug("Request to save Notificacion : {}", notificacionDTO);
        Notificacion notificacion = notificacionMapper.toEntity(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);
        return notificacionMapper.toDto(notificacion);
    }

    @Override
    public NotificacionDTO update(NotificacionDTO notificacionDTO) {
        LOG.debug("Request to update Notificacion : {}", notificacionDTO);
        Notificacion notificacion = notificacionMapper.toEntity(notificacionDTO);
        notificacion = notificacionRepository.save(notificacion);
        return notificacionMapper.toDto(notificacion);
    }

    @Override
    public Optional<NotificacionDTO> partialUpdate(NotificacionDTO notificacionDTO) {
        LOG.debug("Request to partially update Notificacion : {}", notificacionDTO);

        return notificacionRepository
            .findById(notificacionDTO.getId())
            .map(existingNotificacion -> {
                notificacionMapper.partialUpdate(existingNotificacion, notificacionDTO);

                return existingNotificacion;
            })
            .map(notificacionRepository::save)
            .map(notificacionMapper::toDto);
    }

    @Override
    public Page<NotificacionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Notificacions");
        return notificacionRepository.findAll(pageable).map(notificacionMapper::toDto);
    }

    @Override
    public Optional<NotificacionDTO> findOne(String id) {
        LOG.debug("Request to get Notificacion : {}", id);
        return notificacionRepository.findById(id).map(notificacionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Notificacion : {}", id);
        notificacionRepository.deleteById(id);
    }
}
