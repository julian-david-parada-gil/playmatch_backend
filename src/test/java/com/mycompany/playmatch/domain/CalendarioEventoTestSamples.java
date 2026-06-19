package com.mycompany.playmatch.domain;

import java.util.UUID;

public class CalendarioEventoTestSamples {

    public static CalendarioEvento getCalendarioEventoSample1() {
        return new CalendarioEvento().id("id1").titulo("titulo1");
    }

    public static CalendarioEvento getCalendarioEventoSample2() {
        return new CalendarioEvento().id("id2").titulo("titulo2");
    }

    public static CalendarioEvento getCalendarioEventoRandomSampleGenerator() {
        return new CalendarioEvento().id(UUID.randomUUID().toString()).titulo(UUID.randomUUID().toString());
    }
}
