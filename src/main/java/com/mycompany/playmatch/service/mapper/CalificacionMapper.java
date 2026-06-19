package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Calificacion;
import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.CalificacionDTO;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Calificacion} and its DTO {@link CalificacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalificacionMapper extends EntityMapper<CalificacionDTO, Calificacion> {
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    @Mapping(target = "autor", source = "autor", qualifiedByName = "cuentaId")
    CalificacionDTO toDto(Calificacion s);

    @Named("torneoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    TorneoDTO toDtoTorneoNombre(Torneo torneo);

    @Named("cuentaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaDTO toDtoCuentaId(Cuenta cuenta);
}
