package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.MiembroGrupo;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.dto.MiembroGrupoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MiembroGrupo} and its DTO {@link MiembroGrupoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MiembroGrupoMapper extends EntityMapper<MiembroGrupoDTO, MiembroGrupo> {
    @Mapping(target = "grupo", source = "grupo", qualifiedByName = "grupoNombre")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "cuentaId")
    MiembroGrupoDTO toDto(MiembroGrupo s);

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
