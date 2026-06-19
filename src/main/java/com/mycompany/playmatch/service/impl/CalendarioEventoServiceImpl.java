package com.mycompany.playmatch.service.impl;

import com.mycompany.playmatch.domain.CalendarioEvento;
import com.mycompany.playmatch.repository.CalendarioEventoRepository;
import com.mycompany.playmatch.service.CalendarioEventoService;
import com.mycompany.playmatch.service.dto.CalendarioEventoDTO;
import com.mycompany.playmatch.service.mapper.CalendarioEventoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.mycompany.playmatch.domain.CalendarioEvento}.
 */
@Service
public class CalendarioEventoServiceImpl implements CalendarioEventoService {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarioEventoServiceImpl.class);

    private final CalendarioEventoRepository calendarioEventoRepository;

    private final CalendarioEventoMapper calendarioEventoMapper;

    public CalendarioEventoServiceImpl(
        CalendarioEventoRepository calendarioEventoRepository,
        CalendarioEventoMapper calendarioEventoMapper
    ) {
        this.calendarioEventoRepository = calendarioEventoRepository;
        this.calendarioEventoMapper = calendarioEventoMapper;
    }

    @Override
    public CalendarioEventoDTO save(CalendarioEventoDTO calendarioEventoDTO) {
        LOG.debug("Request to save CalendarioEvento : {}", calendarioEventoDTO);
        CalendarioEvento calendarioEvento = calendarioEventoMapper.toEntity(calendarioEventoDTO);
        calendarioEvento = calendarioEventoRepository.save(calendarioEvento);
        return calendarioEventoMapper.toDto(calendarioEvento);
    }

    @Override
    public CalendarioEventoDTO update(CalendarioEventoDTO calendarioEventoDTO) {
        LOG.debug("Request to update CalendarioEvento : {}", calendarioEventoDTO);
        CalendarioEvento calendarioEvento = calendarioEventoMapper.toEntity(calendarioEventoDTO);
        calendarioEvento = calendarioEventoRepository.save(calendarioEvento);
        return calendarioEventoMapper.toDto(calendarioEvento);
    }

    @Override
    public Optional<CalendarioEventoDTO> partialUpdate(CalendarioEventoDTO calendarioEventoDTO) {
        LOG.debug("Request to partially update CalendarioEvento : {}", calendarioEventoDTO);

        return calendarioEventoRepository
            .findById(calendarioEventoDTO.getId())
            .map(existingCalendarioEvento -> {
                calendarioEventoMapper.partialUpdate(existingCalendarioEvento, calendarioEventoDTO);

                return existingCalendarioEvento;
            })
            .map(calendarioEventoRepository::save)
            .map(calendarioEventoMapper::toDto);
    }

    @Override
    public Page<CalendarioEventoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CalendarioEventos");
        return calendarioEventoRepository.findAll(pageable).map(calendarioEventoMapper::toDto);
    }

    public Page<CalendarioEventoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return calendarioEventoRepository.findAllWithEagerRelationships(pageable).map(calendarioEventoMapper::toDto);
    }

    @Override
    public Optional<CalendarioEventoDTO> findOne(String id) {
        LOG.debug("Request to get CalendarioEvento : {}", id);
        return calendarioEventoRepository.findOneWithEagerRelationships(id).map(calendarioEventoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete CalendarioEvento : {}", id);
        calendarioEventoRepository.deleteById(id);
    }
}
