package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.MensajeGrupo;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.dto.MensajeGrupoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MensajeGrupo} and its DTO {@link MensajeGrupoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MensajeGrupoMapper extends EntityMapper<MensajeGrupoDTO, MensajeGrupo> {
    @Mapping(target = "grupo", source = "grupo", qualifiedByName = "grupoNombre")
    @Mapping(target = "autor", source = "autor", qualifiedByName = "cuentaId")
    MensajeGrupoDTO toDto(MensajeGrupo s);

    @Named("grupoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    GrupoDTO toDtoGrupoNombre(Grupo grupo);

    @Named("cuentaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaDTO toDtoCuentaId(Cuenta cuenta);
}
