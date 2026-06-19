package com.mycompany.playmatch.domain;

import java.util.UUID;

public class MiembroGrupoTestSamples {

    public static MiembroGrupo getMiembroGrupoSample1() {
        return new MiembroGrupo().id("id1");
    }

    public static MiembroGrupo getMiembroGrupoSample2() {
        return new MiembroGrupo().id("id2");
    }

    public static MiembroGrupo getMiembroGrupoRandomSampleGenerator() {
        return new MiembroGrupo().id(UUID.randomUUID().toString());
    }
}
