package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.NotificacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notificacion.class);
        Notificacion notificacion1 = getNotificacionSample1();
        Notificacion notificacion2 = new Notificacion();
        assertThat(notificacion1).isNotEqualTo(notificacion2);

        notificacion2.setId(notificacion1.getId());
        assertThat(notificacion1).isEqualTo(notificacion2);

        notificacion2 = getNotificacionSample2();
        assertThat(notificacion1).isNotEqualTo(notificacion2);
    }

    @Test
    void destinatarioTest() {
        Notificacion notificacion = getNotificacionRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        notificacion.setDestinatario(cuentaBack);
        assertThat(notificacion.getDestinatario()).isEqualTo(cuentaBack);

        notificacion.destinatario(null);
        assertThat(notificacion.getDestinatario()).isNull();
    }
}
