package com.mycompany.playmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PartidoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Partido getPartidoSample1() {
        return new Partido().id("id1").lugar("lugar1").tiempoMinutos(1).marcadorLocal(1).marcadorVisitante(1);
    }

    public static Partido getPartidoSample2() {
        return new Partido().id("id2").lugar("lugar2").tiempoMinutos(2).marcadorLocal(2).marcadorVisitante(2);
    }

    public static Partido getPartidoRandomSampleGenerator() {
        return new Partido()
            .id(UUID.randomUUID().toString())
            .lugar(UUID.randomUUID().toString())
            .tiempoMinutos(intCount.incrementAndGet())
            .marcadorLocal(intCount.incrementAndGet())
            .marcadorVisitante(intCount.incrementAndGet());
    }
}
