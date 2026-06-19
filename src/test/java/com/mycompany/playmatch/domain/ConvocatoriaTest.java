package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.ConvocatoriaTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConvocatoriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convocatoria.class);
        Convocatoria convocatoria1 = getConvocatoriaSample1();
        Convocatoria convocatoria2 = new Convocatoria();
        assertThat(convocatoria1).isNotEqualTo(convocatoria2);

        convocatoria2.setId(convocatoria1.getId());
        assertThat(convocatoria1).isEqualTo(convocatoria2);

        convocatoria2 = getConvocatoriaSample2();
        assertThat(convocatoria1).isNotEqualTo(convocatoria2);
    }

    @Test
    void torneoTest() {
        Convocatoria convocatoria = getConvocatoriaRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        convocatoria.setTorneo(torneoBack);
        assertThat(convocatoria.getTorneo()).isEqualTo(torneoBack);

        convocatoria.torneo(null);
        assertThat(convocatoria.getTorneo()).isNull();
    }
}
