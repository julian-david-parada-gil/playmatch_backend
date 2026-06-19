package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Convocatoria;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.ConvocatoriaDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Convocatoria} and its DTO {@link ConvocatoriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConvocatoriaMapper extends EntityMapper<ConvocatoriaDTO, Convocatoria> {
    @Mapping(target = "torneo", source = "torneo", qualifiedByName = "torneoNombre")
    ConvocatoriaDTO toDto(Convocatoria s);

    @Named("torneoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    TorneoDTO toDtoTorneoNombre(Torneo torneo);
}
