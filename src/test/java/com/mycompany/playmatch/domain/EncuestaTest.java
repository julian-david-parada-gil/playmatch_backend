package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.EncuestaTestSamples.*;
import static com.mycompany.playmatch.domain.GrupoTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EncuestaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Encuesta.class);
        Encuesta encuesta1 = getEncuestaSample1();
        Encuesta encuesta2 = new Encuesta();
        assertThat(encuesta1).isNotEqualTo(encuesta2);

        encuesta2.setId(encuesta1.getId());
        assertThat(encuesta1).isEqualTo(encuesta2);

        encuesta2 = getEncuestaSample2();
        assertThat(encuesta1).isNotEqualTo(encuesta2);
    }

    @Test
    void grupoTest() {
        Encuesta encuesta = getEncuestaRandomSampleGenerator();
        Grupo grupoBack = getGrupoRandomSampleGenerator();

        encuesta.setGrupo(grupoBack);
        assertThat(encuesta.getGrupo()).isEqualTo(grupoBack);

        encuesta.grupo(null);
        assertThat(encuesta.getGrupo()).isNull();
    }

    @Test
    void torneoTest() {
        Encuesta encuesta = getEncuestaRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        encuesta.setTorneo(torneoBack);
        assertThat(encuesta.getTorneo()).isEqualTo(torneoBack);

        encuesta.torneo(null);
        assertThat(encuesta.getTorneo()).isNull();
    }
}
