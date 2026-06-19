package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.GrupoAsserts.*;
import static com.mycompany.playmatch.domain.GrupoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrupoMapperTest {

    private GrupoMapper grupoMapper;

    @BeforeEach
    void setUp() {
        grupoMapper = new GrupoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGrupoSample1();
        var actual = grupoMapper.toEntity(grupoMapper.toDto(expected));
        assertGrupoAllPropertiesEquals(expected, actual);
    }
}
