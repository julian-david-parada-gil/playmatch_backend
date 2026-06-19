package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.NotificacionAsserts.*;
import static com.mycompany.playmatch.domain.NotificacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificacionMapperTest {

    private NotificacionMapper notificacionMapper;

    @BeforeEach
    void setUp() {
        notificacionMapper = new NotificacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNotificacionSample1();
        var actual = notificacionMapper.toEntity(notificacionMapper.toDto(expected));
        assertNotificacionAllPropertiesEquals(expected, actual);
    }
}
