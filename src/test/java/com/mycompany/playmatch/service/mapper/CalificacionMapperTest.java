package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.CalificacionAsserts.*;
import static com.mycompany.playmatch.domain.CalificacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalificacionMapperTest {

    private CalificacionMapper calificacionMapper;

    @BeforeEach
    void setUp() {
        calificacionMapper = new CalificacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCalificacionSample1();
        var actual = calificacionMapper.toEntity(calificacionMapper.toDto(expected));
        assertCalificacionAllPropertiesEquals(expected, actual);
    }
}
