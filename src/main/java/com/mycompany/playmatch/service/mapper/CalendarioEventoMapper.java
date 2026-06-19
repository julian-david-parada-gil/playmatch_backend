package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.CalendarioEvento;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.CalendarioEventoDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalendarioEvento} and its DTO {@link CalendarioEventoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalendarioEventoMapper extends EntityMapper<CalendarioEventoDTO, CalendarioEvento> {
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    CalendarioEventoDTO toDto(CalendarioEvento s);

    @Named("torneoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    TorneoDTO toDtoTorneoNombre(Torneo torneo);
}
