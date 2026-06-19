package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CalendarioEventoTestSamples.*;
import static com.mycompany.playmatch.domain.CalificacionTestSamples.*;
import static com.mycompany.playmatch.domain.CategoriaTestSamples.*;
import static com.mycompany.playmatch.domain.ConvocatoriaTestSamples.*;
import static com.mycompany.playmatch.domain.EncuestaTestSamples.*;
import static com.mycompany.playmatch.domain.InscripcionTestSamples.*;
import static com.mycompany.playmatch.domain.PartidoTestSamples.*;
import static com.mycompany.playmatch.domain.TablaPosicionTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TorneoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Torneo.class);
        Torneo torneo1 = getTorneoSample1();
        Torneo torneo2 = new Torneo();
        assertThat(torneo1).isNotEqualTo(torneo2);

        torneo2.setId(torneo1.getId());
        assertThat(torneo1).isEqualTo(torneo2);

        torneo2 = getTorneoSample2();
        assertThat(torneo1).isNotEqualTo(torneo2);
    }

    @Test
    void inscripcionesTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        Inscripcion inscripcionBack = getInscripcionRandomSampleGenerator();

        torneo.addInscripciones(inscripcionBack);
        assertThat(torneo.getInscripcioneses()).containsOnly(inscripcionBack);
        assertThat(inscripcionBack.getTorneo()).isEqualTo(torneo);

        torneo.removeInscripciones(inscripcionBack);
        assertThat(torneo.getInscripcioneses()).doesNotContain(inscripcionBack);
        assertThat(inscripcionBack.getTorneo()).isNull();

        torneo.inscripcioneses(new HashSet<>(Set.of(inscripcionBack)));
        assertThat(torneo.getInscripcioneses()).containsOnly(inscripcionBack);
        assertThat(inscripcionBack.getTorneo()).isEqualTo(torneo);

        torneo.setInscripcioneses(new HashSet<>());
        assertThat(torneo.getInscripcioneses()).doesNotContain(inscripcionBack);
        assertThat(inscripcionBack.getTorneo()).isNull();
    }

    @Test
    void partidosTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        Partido partidoBack = getPartidoRandomSampleGenerator();

        torneo.addPartidos(partidoBack);
        assertThat(torneo.getPartidoses()).containsOnly(partidoBack);
        assertThat(partidoBack.getTorneo()).isEqualTo(torneo);

        torneo.removePartidos(partidoBack);
        assertThat(torneo.getPartidoses()).doesNotContain(partidoBack);
        assertThat(partidoBack.getTorneo()).isNull();

        torneo.partidoses(new HashSet<>(Set.of(partidoBack)));
        assertThat(torneo.getPartidoses()).containsOnly(partidoBack);
        assertThat(partidoBack.getTorneo()).isEqualTo(torneo);

        torneo.setPartidoses(new HashSet<>());
        assertThat(torneo.getPartidoses()).doesNotContain(partidoBack);
        assertThat(partidoBack.getTorneo()).isNull();
    }

    @Test
    void tablaPosicionesTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        TablaPosicion tablaPosicionBack = getTablaPosicionRandomSampleGenerator();

        torneo.addTablaPosiciones(tablaPosicionBack);
        assertThat(torneo.getTablaPosicioneses()).containsOnly(tablaPosicionBack);
        assertThat(tablaPosicionBack.getTorneo()).isEqualTo(torneo);

        torneo.removeTablaPosiciones(tablaPosicionBack);
        assertThat(torneo.getTablaPosicioneses()).doesNotContain(tablaPosicionBack);
        assertThat(tablaPosicionBack.getTorneo()).isNull();

        torneo.tablaPosicioneses(new HashSet<>(Set.of(tablaPosicionBack)));
        assertThat(torneo.getTablaPosicioneses()).containsOnly(tablaPosicionBack);
        assertThat(tablaPosicionBack.getTorneo()).isEqualTo(torneo);

        torneo.setTablaPosicioneses(new HashSet<>());
        assertThat(torneo.getTablaPosicioneses()).doesNotContain(tablaPosicionBack);
        assertThat(tablaPosicionBack.getTorneo()).isNull();
    }

    @Test
    void encuestasTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        Encuesta encuestaBack = getEncuestaRandomSampleGenerator();

        torneo.addEncuestas(encuestaBack);
        assertThat(torneo.getEncuestases()).containsOnly(encuestaBack);
        assertThat(encuestaBack.getTorneo()).isEqualTo(torneo);

        torneo.removeEncuestas(encuestaBack);
        assertThat(torneo.getEncuestases()).doesNotContain(encuestaBack);
        assertThat(encuestaBack.getTorneo()).isNull();

        torneo.encuestases(new HashSet<>(Set.of(encuestaBack)));
        assertThat(torneo.getEncuestases()).containsOnly(encuestaBack);
        assertThat(encuestaBack.getTorneo()).isEqualTo(torneo);

        torneo.setEncuestases(new HashSet<>());
        assertThat(torneo.getEncuestases()).doesNotContain(encuestaBack);
        assertThat(encuestaBack.getTorneo()).isNull();
    }

    @Test
    void calificacionesTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        Calificacion calificacionBack = getCalificacionRandomSampleGenerator();

        torneo.addCalificaciones(calificacionBack);
        assertThat(torneo.getCalificacioneses()).containsOnly(calificacionBack);
        assertThat(calificacionBack.getTorneo()).isEqualTo(torneo);

        torneo.removeCalificaciones(calificacionBack);
        assertThat(torneo.getCalificacioneses()).doesNotContain(calificacionBack);
        assertThat(calificacionBack.getTorneo()).isNull();

        torneo.calificacioneses(new HashSet<>(Set.of(calificacionBack)));
        assertThat(torneo.getCalificacioneses()).containsOnly(calificacionBack);
        assertThat(calificacionBack.getTorneo()).isEqualTo(torneo);

        torneo.setCalificacioneses(new HashSet<>());
        assertThat(torneo.getCalificacioneses()).doesNotContain(calificacionBack);
        assertThat(calificacionBack.getTorneo()).isNull();
    }

    @Test
    void convocatoriasTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        Convocatoria convocatoriaBack = getConvocatoriaRandomSampleGenerator();

        torneo.addConvocatorias(convocatoriaBack);
        assertThat(torneo.getConvocatoriases()).containsOnly(convocatoriaBack);
        assertThat(convocatoriaBack.getTorneo()).isEqualTo(torneo);

        torneo.removeConvocatorias(convocatoriaBack);
        assertThat(torneo.getConvocatoriases()).doesNotContain(convocatoriaBack);
        assertThat(convocatoriaBack.getTorneo()).isNull();

        torneo.convocatoriases(new HashSet<>(Set.of(convocatoriaBack)));
        assertThat(torneo.getConvocatoriases()).containsOnly(convocatoriaBack);
        assertThat(convocatoriaBack.getTorneo()).isEqualTo(torneo);

        torneo.setConvocatoriases(new HashSet<>());
        assertThat(torneo.getConvocatoriases()).doesNotContain(convocatoriaBack);
        assertThat(convocatoriaBack.getTorneo()).isNull();
    }

    @Test
    void eventosCalendarioTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        CalendarioEvento calendarioEventoBack = getCalendarioEventoRandomSampleGenerator();

        torneo.addEventosCalendario(calendarioEventoBack);
        assertThat(torneo.getEventosCalendarios()).containsOnly(calendarioEventoBack);
        assertThat(calendarioEventoBack.getTorneo()).isEqualTo(torneo);

        torneo.removeEventosCalendario(calendarioEventoBack);
        assertThat(torneo.getEventosCalendarios()).doesNotContain(calendarioEventoBack);
        assertThat(calendarioEventoBack.getTorneo()).isNull();

        torneo.eventosCalendarios(new HashSet<>(Set.of(calendarioEventoBack)));
        assertThat(torneo.getEventosCalendarios()).containsOnly(calendarioEventoBack);
        assertThat(calendarioEventoBack.getTorneo()).isEqualTo(torneo);

        torneo.setEventosCalendarios(new HashSet<>());
        assertThat(torneo.getEventosCalendarios()).doesNotContain(calendarioEventoBack);
        assertThat(calendarioEventoBack.getTorneo()).isNull();
    }

    @Test
    void categoriaTest() {
        Torneo torneo = getTorneoRandomSampleGenerator();
        Categoria categoriaBack = getCategoriaRandomSampleGenerator();

        torneo.setCategoria(categoriaBack);
        assertThat(torneo.getCategoria()).isEqualTo(categoriaBack);

        torneo.categoria(null);
        assertThat(torneo.getCategoria()).isNull();
    }
}
