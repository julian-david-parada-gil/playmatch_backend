package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Noticia;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.NoticiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Noticia} and its DTO {@link NoticiaDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoticiaMapper extends EntityMapper<NoticiaDTO, Noticia> {
    @Mapping(target = "autor", source = "autor", qualifiedByName = "cuentaId")
    NoticiaDTO toDto(Noticia s);

    @Named("cuentaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaDTO toDtoCuentaId(Cuenta cuenta);
}
