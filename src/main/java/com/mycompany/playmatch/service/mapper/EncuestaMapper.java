package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Encuesta;
import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.EncuestaDTO;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Encuesta} and its DTO {@link EncuestaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EncuestaMapper extends EntityMapper<EncuestaDTO, Encuesta> {
    @Mapping(target = "grupo", source = "grupo", qualifiedByName = "grupoNombre")
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    EncuestaDTO toDto(Encuesta s);

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
