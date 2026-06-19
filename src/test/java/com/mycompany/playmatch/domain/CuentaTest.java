package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CalificacionTestSamples.*;
import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.InscripcionTestSamples.*;
import static com.mycompany.playmatch.domain.MensajeGrupoTestSamples.*;
import static com.mycompany.playmatch.domain.MiembroGrupoTestSamples.*;
import static com.mycompany.playmatch.domain.NoticiaTestSamples.*;
import static com.mycompany.playmatch.domain.NotificacionTestSamples.*;
import static com.mycompany.playmatch.domain.TipoDocumentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CuentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuenta.class);
        Cuenta cuenta1 = getCuentaSample1();
        Cuenta cuenta2 = new Cuenta();
        assertThat(cuenta1).isNotEqualTo(cuenta2);

        cuenta2.setId(cuenta1.getId());
        assertThat(cuenta1).isEqualTo(cuenta2);

        cuenta2 = getCuentaSample2();
        assertThat(cuenta1).isNotEqualTo(cuenta2);
    }

    @Test
    void gruposTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        MiembroGrupo miembroGrupoBack = getMiembroGrupoRandomSampleGenerator();

        cuenta.addGrupos(miembroGrupoBack);
        assertThat(cuenta.getGruposes()).containsOnly(miembroGrupoBack);
        assertThat(miembroGrupoBack.getUsuario()).isEqualTo(cuenta);

        cuenta.removeGrupos(miembroGrupoBack);
        assertThat(cuenta.getGruposes()).doesNotContain(miembroGrupoBack);
        assertThat(miembroGrupoBack.getUsuario()).isNull();

        cuenta.gruposes(new HashSet<>(Set.of(miembroGrupoBack)));
        assertThat(cuenta.getGruposes()).containsOnly(miembroGrupoBack);
        assertThat(miembroGrupoBack.getUsuario()).isEqualTo(cuenta);

        cuenta.setGruposes(new HashSet<>());
        assertThat(cuenta.getGruposes()).doesNotContain(miembroGrupoBack);
        assertThat(miembroGrupoBack.getUsuario()).isNull();
    }

    @Test
    void inscripcionesTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        Inscripcion inscripcionBack = getInscripcionRandomSampleGenerator();

        cuenta.addInscripciones(inscripcionBack);
        assertThat(cuenta.getInscripcioneses()).containsOnly(inscripcionBack);
        assertThat(inscripcionBack.getUsuario()).isEqualTo(cuenta);

        cuenta.removeInscripciones(inscripcionBack);
        assertThat(cuenta.getInscripcioneses()).doesNotContain(inscripcionBack);
        assertThat(inscripcionBack.getUsuario()).isNull();

        cuenta.inscripcioneses(new HashSet<>(Set.of(inscripcionBack)));
        assertThat(cuenta.getInscripcioneses()).containsOnly(inscripcionBack);
        assertThat(inscripcionBack.getUsuario()).isEqualTo(cuenta);

        cuenta.setInscripcioneses(new HashSet<>());
        assertThat(cuenta.getInscripcioneses()).doesNotContain(inscripcionBack);
        assertThat(inscripcionBack.getUsuario()).isNull();
    }

    @Test
    void mensajesTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        MensajeGrupo mensajeGrupoBack = getMensajeGrupoRandomSampleGenerator();

        cuenta.addMensajes(mensajeGrupoBack);
        assertThat(cuenta.getMensajeses()).containsOnly(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getAutor()).isEqualTo(cuenta);

        cuenta.removeMensajes(mensajeGrupoBack);
        assertThat(cuenta.getMensajeses()).doesNotContain(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getAutor()).isNull();

        cuenta.mensajeses(new HashSet<>(Set.of(mensajeGrupoBack)));
        assertThat(cuenta.getMensajeses()).containsOnly(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getAutor()).isEqualTo(cuenta);

        cuenta.setMensajeses(new HashSet<>());
        assertThat(cuenta.getMensajeses()).doesNotContain(mensajeGrupoBack);
        assertThat(mensajeGrupoBack.getAutor()).isNull();
    }

    @Test
    void calificacionesTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        Calificacion calificacionBack = getCalificacionRandomSampleGenerator();

        cuenta.addCalificaciones(calificacionBack);
        assertThat(cuenta.getCalificacioneses()).containsOnly(calificacionBack);
        assertThat(calificacionBack.getAutor()).isEqualTo(cuenta);

        cuenta.removeCalificaciones(calificacionBack);
        assertThat(cuenta.getCalificacioneses()).doesNotContain(calificacionBack);
        assertThat(calificacionBack.getAutor()).isNull();

        cuenta.calificacioneses(new HashSet<>(Set.of(calificacionBack)));
        assertThat(cuenta.getCalificacioneses()).containsOnly(calificacionBack);
        assertThat(calificacionBack.getAutor()).isEqualTo(cuenta);

        cuenta.setCalificacioneses(new HashSet<>());
        assertThat(cuenta.getCalificacioneses()).doesNotContain(calificacionBack);
        assertThat(calificacionBack.getAutor()).isNull();
    }

    @Test
    void noticiasTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        Noticia noticiaBack = getNoticiaRandomSampleGenerator();

        cuenta.addNoticias(noticiaBack);
        assertThat(cuenta.getNoticiases()).containsOnly(noticiaBack);
        assertThat(noticiaBack.getAutor()).isEqualTo(cuenta);

        cuenta.removeNoticias(noticiaBack);
        assertThat(cuenta.getNoticiases()).doesNotContain(noticiaBack);
        assertThat(noticiaBack.getAutor()).isNull();

        cuenta.noticiases(new HashSet<>(Set.of(noticiaBack)));
        assertThat(cuenta.getNoticiases()).containsOnly(noticiaBack);
        assertThat(noticiaBack.getAutor()).isEqualTo(cuenta);

        cuenta.setNoticiases(new HashSet<>());
        assertThat(cuenta.getNoticiases()).doesNotContain(noticiaBack);
        assertThat(noticiaBack.getAutor()).isNull();
    }

    @Test
    void notificacionesTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        Notificacion notificacionBack = getNotificacionRandomSampleGenerator();

        cuenta.addNotificaciones(notificacionBack);
        assertThat(cuenta.getNotificacioneses()).containsOnly(notificacionBack);
        assertThat(notificacionBack.getDestinatario()).isEqualTo(cuenta);

        cuenta.removeNotificaciones(notificacionBack);
        assertThat(cuenta.getNotificacioneses()).doesNotContain(notificacionBack);
        assertThat(notificacionBack.getDestinatario()).isNull();

        cuenta.notificacioneses(new HashSet<>(Set.of(notificacionBack)));
        assertThat(cuenta.getNotificacioneses()).containsOnly(notificacionBack);
        assertThat(notificacionBack.getDestinatario()).isEqualTo(cuenta);

        cuenta.setNotificacioneses(new HashSet<>());
        assertThat(cuenta.getNotificacioneses()).doesNotContain(notificacionBack);
        assertThat(notificacionBack.getDestinatario()).isNull();
    }

    @Test
    void tipoDocumentoTest() {
        Cuenta cuenta = getCuentaRandomSampleGenerator();
        TipoDocumento tipoDocumentoBack = getTipoDocumentoRandomSampleGenerator();

        cuenta.setTipoDocumento(tipoDocumentoBack);
        assertThat(cuenta.getTipoDocumento()).isEqualTo(tipoDocumentoBack);

        cuenta.tipoDocumento(null);
        assertThat(cuenta.getTipoDocumento()).isNull();
    }
}
