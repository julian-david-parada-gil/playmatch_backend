package com.mycompany.playmatch.domain;

import java.util.UUID;

public class MensajeGrupoTestSamples {

    public static MensajeGrupo getMensajeGrupoSample1() {
        return new MensajeGrupo().id("id1");
    }

    public static MensajeGrupo getMensajeGrupoSample2() {
        return new MensajeGrupo().id("id2");
    }

    public static MensajeGrupo getMensajeGrupoRandomSampleGenerator() {
        return new MensajeGrupo().id(UUID.randomUUID().toString());
    }
}
