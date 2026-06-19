package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.GrupoTestSamples.*;
import static com.mycompany.playmatch.domain.MiembroGrupoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MiembroGrupoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiembroGrupo.class);
        MiembroGrupo miembroGrupo1 = getMiembroGrupoSample1();
        MiembroGrupo miembroGrupo2 = new MiembroGrupo();
        assertThat(miembroGrupo1).isNotEqualTo(miembroGrupo2);

        miembroGrupo2.setId(miembroGrupo1.getId());
        assertThat(miembroGrupo1).isEqualTo(miembroGrupo2);

        miembroGrupo2 = getMiembroGrupoSample2();
        assertThat(miembroGrupo1).isNotEqualTo(miembroGrupo2);
    }

    @Test
    void grupoTest() {
        MiembroGrupo miembroGrupo = getMiembroGrupoRandomSampleGenerator();
        Grupo grupoBack = getGrupoRandomSampleGenerator();

        miembroGrupo.setGrupo(grupoBack);
        assertThat(miembroGrupo.getGrupo()).isEqualTo(grupoBack);

        miembroGrupo.grupo(null);
        assertThat(miembroGrupo.getGrupo()).isNull();
    }

    @Test
    void usuarioTest() {
        MiembroGrupo miembroGrupo = getMiembroGrupoRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        miembroGrupo.setUsuario(cuentaBack);
        assertThat(miembroGrupo.getUsuario()).isEqualTo(cuentaBack);

        miembroGrupo.usuario(null);
        assertThat(miembroGrupo.getUsuario()).isNull();
    }
}
