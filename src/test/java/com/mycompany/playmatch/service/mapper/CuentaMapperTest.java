package com.mycompany.playmatch.service.mapper;

import static com.mycompany.playmatch.domain.CuentaAsserts.*;
import static com.mycompany.playmatch.domain.CuentaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuentaMapperTest {

    private CuentaMapper cuentaMapper;

    @BeforeEach
    void setUp() {
        cuentaMapper = new CuentaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCuentaSample1();
        var actual = cuentaMapper.toEntity(cuentaMapper.toDto(expected));
        assertCuentaAllPropertiesEquals(expected, actual);
    }
}
