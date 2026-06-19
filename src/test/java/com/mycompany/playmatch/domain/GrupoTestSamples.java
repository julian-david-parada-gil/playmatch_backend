package com.mycompany.playmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GrupoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Grupo getGrupoSample1() {
        return new Grupo().id("id1").nombre("nombre1").limiteParticipantes(1);
    }

    public static Grupo getGrupoSample2() {
        return new Grupo().id("id2").nombre("nombre2").limiteParticipantes(2);
    }

    public static Grupo getGrupoRandomSampleGenerator() {
        return new Grupo()
            .id(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .limiteParticipantes(intCount.incrementAndGet());
    }
}
