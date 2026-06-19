package com.mycompany.playmatch.domain;

import java.util.UUID;

public class NotificacionTestSamples {

    public static Notificacion getNotificacionSample1() {
        return new Notificacion().id("id1").titulo("titulo1");
    }

    public static Notificacion getNotificacionSample2() {
        return new Notificacion().id("id2").titulo("titulo2");
    }

    public static Notificacion getNotificacionRandomSampleGenerator() {
        return new Notificacion().id(UUID.randomUUID().toString()).titulo(UUID.randomUUID().toString());
    }
}
