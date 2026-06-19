package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.InscripcionAsserts.*;
import static com.mycompany.playmatch.domain.InscripcionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InscripcionMapperTest {

    private InscripcionMapper inscripcionMapper;

    @BeforeEach
    void setUp() {
        inscripcionMapper = new InscripcionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInscripcionSample1();
        var actual = inscripcionMapper.toEntity(inscripcionMapper.toDto(expected));
        assertInscripcionAllPropertiesEquals(expected, actual);
    }
}
