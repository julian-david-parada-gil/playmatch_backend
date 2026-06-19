package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Categoria;
import com.mycompany.playmatch.service.dto.CategoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {}
