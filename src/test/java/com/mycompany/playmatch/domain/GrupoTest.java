package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.EncuestaTestSamples.*;
import static com.mycompany.playmatch.domain.GrupoTestSamples.*;
import static com.mycompany.playmatch.domain.MensajeGrupoTestSamples.*;
import static com.mycompany.playmatch.domain.MiembroGrupoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GrupoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grupo.class);
        Grupo grupo1 = getGrupoSample1();
        Grupo grupo2 = new Grupo();
        assertThat(grupo1).isNotEqualTo(grupo2);

        grupo2.setId(grupo1.getId());
        assertThat(grupo1).isEqualTo(grupo2);

        grupo2 = getGrupoSample2();
        assertThat(grupo1).isNotEqualTo(grupo2);
    }

    @Test
    void miembrosTest() {
        Grupo grupo = getGrupoRandomSampleGenerator();
        MiembroGrupo miembroGrupoBack = getMiembroGrupoRandomSampleGenerator();

        grupo.addMiembros(miembroGrupoBack);
        assertThat(grupo.getMiembroses()).containsOnly(miembroGrupoBack);
        assertThat(miembroGrupoBack.getGrupo()).isEqualTo(grupo);

        grupo.removeMiembros(miembroGrupoBack);
        assertThat(grupo.getMiembroses()).doesNotContain(miembroGrupoBack);
        assertThat(miembroGrupoBack.getGrupo()).isNull();

        grupo.miembroses(new HashSet<>(Set.of(miembroGrupoBack)));
        assertThat(grupo.getMiembroses()).containsOnly(miembroGrupoBack);
        assertThat(miembroGrupoBack.getGrupo()).isEqualTo(grupo);

        grupo.setMiembroses(new HashSet<>());
        assertThat(grupo.getMiembroses()).doesNotContain(miembroGrupoBack);
        assertThat(miembroGrupoBack.getGrupo()).isNull();
    }

    @Test
    void mensajesTest() {
        Grupo grupo = getGrupoRandomSampleGenerator();
        MensajeGrupo mensajeGrupoBack = getMensajeGrupoRandomSampleGenerator();

        grupo.addMensajes(mensajeGrupoBack);
        assertThat(grupo.getMensajeses()).containsOnly(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getGrupo()).isEqualTo(grupo);

        grupo.removeMensajes(mensajeGrupoBack);
        assertThat(grupo.getMensajeses()).doesNotContain(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getGrupo()).isNull();

        grupo.mensajeses(new HashSet<>(Set.of(mensajeGrupoBack)));
        assertThat(grupo.getMensajeses()).containsOnly(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getGrupo()).isEqualTo(grupo);

        grupo.setMensajeses(new HashSet<>());
        assertThat(grupo.getMensajeses()).doesNotContain(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getGrupo()).isNull();
    }

    @Test
    void encuestasTest() {
        Grupo grupo = getGrupoRandomSampleGenerator();
        Encuesta encuestaBack = getEncuestaRandomSampleGenerator();

        grupo.addEncuestas(encuestaBack);
        assertThat(grupo.getEncuestases()).containsOnly(encuestaBack);
        assertThat(encuestaBack.getGrupo()).isEqualTo(grupo);

        grupo.removeEncuestas(encuestaBack);
        assertThat(grupo.getEncuestases()).doesNotContain(encuestaBack);
        assertThat(encuestaBack.getGrupo()).isNull();

        grupo.encuestases(new HashSet<>(Set.of(encuestaBack)));
        assertThat(grupo.getEncuestases()).containsOnly(encuestaBack);
        assertThat(encuestaBack.getGrupo()).isEqualTo(grupo);

        grupo.setEncuestases(new HashSet<>());
        assertThat(grupo.getEncuestases()).doesNotContain(encuestaBack);
        assertThat(encuestaBack.getGrupo()).isNull();
    }
}
