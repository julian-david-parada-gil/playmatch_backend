package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.TablaPosicionAsserts.*;
import static com.mycompany.playmatch.domain.TablaPosicionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TablaPosicionMapperTest {

    private TablaPosicionMapper tablaPosicionMapper;

    @BeforeEach
    void setUp() {
        tablaPosicionMapper = new TablaPosicionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTablaPosicionSample1();
        var actual = tablaPosicionMapper.toEntity(tablaPosicionMapper.toDto(expected));
        assertTablaPosicionAllPropertiesEquals(expected, actual);
    }
}
