package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CalendarioEventoTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalendarioEventoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarioEvento.class);
        CalendarioEvento calendarioEvento1 = getCalendarioEventoSample1();
        CalendarioEvento calendarioEvento2 = new CalendarioEvento();
        assertThat(calendarioEvento1).isNotEqualTo(calendarioEvento2);

        calendarioEvento2.setId(calendarioEvento1.getId());
        assertThat(calendarioEvento1).isEqualTo(calendarioEvento2);

        calendarioEvento2 = getCalendarioEventoSample2();
        assertThat(calendarioEvento1).isNotEqualTo(calendarioEvento2);
    }

    @Test
    void torneoTest() {
        CalendarioEvento calendarioEvento = getCalendarioEventoRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        calendarioEvento.setTorneo(torneoBack);
        assertThat(calendarioEvento.getTorneo()).isEqualTo(torneoBack);

        calendarioEvento.torneo(null);
        assertThat(calendarioEvento.getTorneo()).isNull();
    }
}
