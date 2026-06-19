package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.MensajeGrupoAsserts.*;
import static com.mycompany.playmatch.domain.MensajeGrupoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MensajeGrupoMapperTest {

    private MensajeGrupoMapper mensajeGrupoMapper;

    @BeforeEach
    void setUp() {
        mensajeGrupoMapper = new MensajeGrupoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMensajeGrupoSample1();
        var actual = mensajeGrupoMapper.toEntity(mensajeGrupoMapper.toDto(expected));
        assertMensajeGrupoAllPropertiesEquals(expected, actual);
    }
}
