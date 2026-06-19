package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Inscripcion;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.InscripcionDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inscripcion} and its DTO {@link InscripcionDTO}.
 */
@Mapper(componentModel = "spring")
public interface InscripcionMapper extends EntityMapper<InscripcionDTO, Inscripcion> {
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "cuentaId")
    InscripcionDTO toDto(Inscripcion s);

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
