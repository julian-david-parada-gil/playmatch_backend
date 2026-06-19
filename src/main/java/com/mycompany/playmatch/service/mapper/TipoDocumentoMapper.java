package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.TipoDocumento;
import com.mycompany.playmatch.service.dto.TipoDocumentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDocumento} and its DTO {@link TipoDocumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper extends EntityMapper<TipoDocumentoDTO, TipoDocumento> {}
