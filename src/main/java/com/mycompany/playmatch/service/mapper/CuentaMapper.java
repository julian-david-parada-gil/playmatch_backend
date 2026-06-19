package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.TipoDocumento;
import com.mycompany.playmatch.domain.User;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.TipoDocumentoDTO;
import com.mycompany.playmatch.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuenta} and its DTO {@link CuentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuentaMapper extends EntityMapper<CuentaDTO, Cuenta> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "tipoDocumentoNombreDocumento")
    CuentaDTO toDto(Cuenta s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tipoDocumentoNombreDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreDocumento", source = "nombreDocumento")
    TipoDocumentoDTO toDtoTipoDocumentoNombreDocumento(TipoDocumento tipoDocumento);
}
