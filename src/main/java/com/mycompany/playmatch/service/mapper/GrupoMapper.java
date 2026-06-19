package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Grupo;
import com.mycompany.playmatch.service.dto.GrupoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grupo} and its DTO {@link GrupoDTO}.
 */
@Mapper(componentModel = "spring")
public interface GrupoMapper extends EntityMapper<GrupoDTO, Grupo> {}
