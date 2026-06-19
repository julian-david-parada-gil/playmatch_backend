package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.GrupoTestSamples.*;
import static com.mycompany.playmatch.domain.MensajeGrupoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MensajeGrupoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensajeGrupo.class);
        MensajeGrupo mensajeGrupo1 = getMensajeGrupoSample1();
        MensajeGrupo mensajeGrupo2 = new MensajeGrupo();
        assertThat(mensajeGrupo1).isNotEqualTo(mensajeGrupo2);

        mensajeGrupo2.setId(mensajeGrupo1.getId());
        assertThat(mensajeGrupo1).isEqualTo(mensajeGrupo2);

        mensajeGrupo2 = getMensajeGrupoSample2();
        assertThat(mensajeGrupo1).isNotEqualTo(mensajeGrupo2);
    }

    @Test
    void grupoTest() {
        MensajeGrupo mensajeGrupo = getMensajeGrupoRandomSampleGenerator();
        Grupo grupoBack = getGrupoRandomSampleGenerator();

        mensajeGrupo.setGrupo(grupoBack);
        assertThat(mensajeGrupo.getGrupo()).isEqualTo(grupoBack);

        mensajeGrupo.grupo(null);
        assertThat(mensajeGrupo.getGrupo()).isNull();
    }

    @Test
    void autorTest() {
        MensajeGrupo mensajeGrupo = getMensajeGrupoRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        mensajeGrupo.setAutor(cuentaBack);
        assertThat(mensajeGrupo.getAutor()).isEqualTo(cuentaBack);

        mensajeGrupo.autor(null);
        assertThat(mensajeGrupo.getAutor()).isNull();
    }
}
