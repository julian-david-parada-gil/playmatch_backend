package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.Partido;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.dto.PartidoDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partido} and its DTO {@link PartidoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartidoMapper extends EntityMapper<PartidoDTO, Partido> {
    @Mapping(target = "equipolocal", source = "equipolocal", qualifiedByName = "grupoNombre")
    @Mapping(target = "equipovisitante", source = "equipovisitante", qualifiedByName = "grupoNombre")
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    PartidoDTO toDto(Partido s);

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
