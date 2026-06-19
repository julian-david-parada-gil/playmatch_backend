package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.ConvocatoriaAsserts.*;
import static com.mycompany.playmatch.domain.ConvocatoriaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvocatoriaMapperTest {

    private ConvocatoriaMapper convocatoriaMapper;

    @BeforeEach
    void setUp() {
        convocatoriaMapper = new ConvocatoriaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConvocatoriaSample1();
        var actual = convocatoriaMapper.toEntity(convocatoriaMapper.toDto(expected));
        assertConvocatoriaAllPropertiesEquals(expected, actual);
    }
}
