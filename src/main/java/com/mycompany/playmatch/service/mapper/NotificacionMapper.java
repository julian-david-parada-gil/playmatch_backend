package com.mycompany.playmatch.service.mapper;

import com.mycompany.playmatch.domain.Cuenta;
import com.mycompany.playmatch.domain.Notificacion;
import com.mycompany.playmatch.service.dto.CuentaDTO;
import com.mycompany.playmatch.service.dto.NotificacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notificacion} and its DTO {@link NotificacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificacionMapper extends EntityMapper<NotificacionDTO, Notificacion> {
    @Mapping(target = "destinatario", source = "destinatario", qualifiedByName = "cuentaId")
    NotificacionDTO toDto(Notificacion s);

    @Named("cuentaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentaDTO toDtoCuentaId(Cuenta cuenta);
}
