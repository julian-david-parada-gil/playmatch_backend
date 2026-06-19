package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.CalendarioEventoAsserts.*;
import static com.mycompany.playmatch.domain.CalendarioEventoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalendarioEventoMapperTest {

    private CalendarioEventoMapper calendarioEventoMapper;

    @BeforeEach
    void setUp() {
        calendarioEventoMapper = new CalendarioEventoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCalendarioEventoSample1();
        var actual = calendarioEventoMapper.toEntity(calendarioEventoMapper.toDto(expected));
        assertCalendarioEventoAllPropertiesEquals(expected, actual);
    }
}
