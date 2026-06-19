package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.TorneoAsserts.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TorneoMapperTest {

    private TorneoMapper torneoMapper;

    @BeforeEach
    void setUp() {
        torneoMapper = new TorneoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTorneoSample1();
        var actual = torneoMapper.toEntity(torneoMapper.toDto(expected));
        assertTorneoAllPropertiesEquals(expected, actual);
    }
}
