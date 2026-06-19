package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.GrupoTestSamples.*;
import static com.mycompany.playmatch.domain.PartidoTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partido.class);
        Partido partido1 = getPartidoSample1();
        Partido partido2 = new Partido();
        assertThat(partido1).isNotEqualTo(partido2);

        partido2.setId(partido1.getId());
        assertThat(partido1).isEqualTo(partido2);

        partido2 = getPartidoSample2();
        assertThat(partido1).isNotEqualTo(partido2);
    }

    @Test
    void equipolocalTest() {
        Partido partido = getPartidoRandomSampleGenerator();
        Grupo grupoBack = getGrupoRandomSampleGenerator();

        partido.setEquipolocal(grupoBack);
        assertThat(partido.getEquipolocal()).isEqualTo(grupoBack);

        partido.equipolocal(null);
        assertThat(partido.getEquipolocal()).isNull();
    }

    @Test
    void equipovisitanteTest() {
        Partido partido = getPartidoRandomSampleGenerator();
        Grupo grupoBack = getGrupoRandomSampleGenerator();

        partido.setEquipovisitante(grupoBack);
        assertThat(partido.getEquipovisitante()).isEqualTo(grupoBack);

        partido.equipovisitante(null);
        assertThat(partido.getEquipovisitante()).isNull();
    }

    @Test
    void torneoTest() {
        Partido partido = getPartidoRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        partido.setTorneo(torneoBack);
        assertThat(partido.getTorneo()).isEqualTo(torneoBack);

        partido.torneo(null);
        assertThat(partido.getTorneo()).isNull();
    }
}
