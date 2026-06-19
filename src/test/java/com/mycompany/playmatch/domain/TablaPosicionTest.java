package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.GrupoTestSamples.*;
import static com.mycompany.playmatch.domain.TablaPosicionTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TablaPosicionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablaPosicion.class);
        TablaPosicion tablaPosicion1 = getTablaPosicionSample1();
        TablaPosicion tablaPosicion2 = new TablaPosicion();
        assertThat(tablaPosicion1).isNotEqualTo(tablaPosicion2);

        tablaPosicion2.setId(tablaPosicion1.getId());
        assertThat(tablaPosicion1).isEqualTo(tablaPosicion2);

        tablaPosicion2 = getTablaPosicionSample2();
        assertThat(tablaPosicion1).isNotEqualTo(tablaPosicion2);
    }

    @Test
    void grupoTest() {
        TablaPosicion tablaPosicion = getTablaPosicionRandomSampleGenerator();
        Grupo grupoBack = getGrupoRandomSampleGenerator();

        tablaPosicion.setGrupo(grupoBack);
        assertThat(tablaPosicion.getGrupo()).isEqualTo(grupoBack);

        tablaPosicion.grupo(null);
        assertThat(tablaPosicion.getGrupo()).isNull();
    }

    @Test
    void torneoTest() {
        TablaPosicion tablaPosicion = getTablaPosicionRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        tablaPosicion.setTorneo(torneoBack);
        assertThat(tablaPosicion.getTorneo()).isEqualTo(torneoBack);

        tablaPosicion.torneo(null);
        assertThat(tablaPosicion.getTorneo()).isNull();
    }
}
