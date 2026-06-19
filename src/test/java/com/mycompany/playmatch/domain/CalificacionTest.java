package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CalificacionTestSamples.*;
import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalificacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calificacion.class);
        Calificacion calificacion1 = getCalificacionSample1();
        Calificacion calificacion2 = new Calificacion();
        assertThat(calificacion1).isNotEqualTo(calificacion2);

        calificacion2.setId(calificacion1.getId());
        assertThat(calificacion1).isEqualTo(calificacion2);

        calificacion2 = getCalificacionSample2();
        assertThat(calificacion1).isNotEqualTo(calificacion2);
    }

    @Test
    void torneoTest() {
        Calificacion calificacion = getCalificacionRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        calificacion.setTorneo(torneoBack);
        assertThat(calificacion.getTorneo()).isEqualTo(torneoBack);

        calificacion.torneo(null);
        assertThat(calificacion.getTorneo()).isNull();
    }

    @Test
    void autorTest() {
        Calificacion calificacion = getCalificacionRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        calificacion.setAutor(cuentaBack);
        assertThat(calificacion.getAutor()).isEqualTo(cuentaBack);

        calificacion.autor(null);
        assertThat(calificacion.getAutor()).isNull();
    }
}
