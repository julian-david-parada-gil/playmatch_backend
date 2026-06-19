package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Categoria;
import com.mycompany.playmatch.domain.Torneo;
import com.mycompany.playmatch.service.dto.CategoriaDTO;
import com.mycompany.playmatch.service.dto.TorneoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Torneo} and its DTO {@link TorneoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TorneoMapper extends EntityMapper<TorneoDTO, Torneo> {
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "categoriaNombre")
    TorneoDTO toDto(Torneo s);

    @Named("categoriaNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    CategoriaDTO toDtoCategoriaNombre(Categoria categoria);
}
