package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.InscripcionTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscripcionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscripcion.class);
        Inscripcion inscripcion1 = getInscripcionSample1();
        Inscripcion inscripcion2 = new Inscripcion();
        assertThat(inscripcion1).isNotEqualTo(inscripcion2);

        inscripcion2.setId(inscripcion1.getId());
        assertThat(inscripcion1).isEqualTo(inscripcion2);

        inscripcion2 = getInscripcionSample2();
        assertThat(inscripcion1).isNotEqualTo(inscripcion2);
    }

    @Test
    void torneoTest() {
        Inscripcion inscripcion = getInscripcionRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        inscripcion.setTorneo(torneoBack);
        assertThat(inscripcion.getTorneo()).isEqualTo(torneoBack);

        inscripcion.torneo(null);
        assertThat(inscripcion.getTorneo()).isNull();
    }

    @Test
    void usuarioTest() {
        Inscripcion inscripcion = getInscripcionRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        inscripcion.setUsuario(cuentaBack);
        assertThat(inscripcion.getUsuario()).isEqualTo(cuentaBack);

        inscripcion.usuario(null);
        assertThat(inscripcion.getUsuario()).isNull();
    }
}
