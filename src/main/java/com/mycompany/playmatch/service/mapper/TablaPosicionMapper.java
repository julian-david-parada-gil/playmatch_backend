package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.TablaPosicion;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.dto.TablaPosicionDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TablaPosicion} and its DTO {@link TablaPosicionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TablaPosicionMapper extends EntityMapper<TablaPosicionDTO, TablaPosicion> {
    @Mapping(target = "grupo", source = "grupo", qualifiedByName = "grupoNombre")
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    TablaPosicionDTO toDto(TablaPosicion s);

    @Named("grupoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    GrupoDTO toDtoGrupoNombre(Grupo grupo);

    @Named("torneoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    TorneoDTO toDtoTorneoNombre(Torneo torneo);
}
