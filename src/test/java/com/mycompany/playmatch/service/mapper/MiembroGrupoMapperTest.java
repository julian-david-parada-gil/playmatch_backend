package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.MiembroGrupoAsserts.*;
import static com.mycompany.playmatch.domain.MiembroGrupoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MiembroGrupoMapperTest {

    private MiembroGrupoMapper miembroGrupoMapper;

    @BeforeEach
    void setUp() {
        miembroGrupoMapper = new MiembroGrupoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMiembroGrupoSample1();
        var actual = miembroGrupoMapper.toEntity(miembroGrupoMapper.toDto(expected));
        assertMiembroGrupoAllPropertiesEquals(expected, actual);
    }
}
