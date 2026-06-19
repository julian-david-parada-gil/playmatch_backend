package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.EncuestaAsserts.*;
import static com.mycompany.playmatch.domain.EncuestaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EncuestaMapperTest {

    private EncuestaMapper encuestaMapper;

    @BeforeEach
    void setUp() {
        encuestaMapper = new EncuestaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEncuestaSample1();
        var actual = encuestaMapper.toEntity(encuestaMapper.toDto(expected));
        assertEncuestaAllPropertiesEquals(expected, actual);
    }
}
