package com.mycompany.playmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TorneoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Torneo getTorneoSample1() {
        return new Torneo().id("id1").nombre("nombre1").ubicacion("ubicacion1").cupoMaximoEquipos(1).cupoMaximoJugadores(1);
    }

    public static Torneo getTorneoSample2() {
        return new Torneo().id("id2").nombre("nombre2").ubicacion("ubicacion2").cupoMaximoEquipos(2).cupoMaximoJugadores(2);
    }

    public static Torneo getTorneoRandomSampleGenerator() {
        return new Torneo()
            .id(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .ubicacion(UUID.randomUUID().toString())
            .cupoMaximoEquipos(intCount.incrementAndGet())
            .cupoMaximoJugadores(intCount.incrementAndGet());
    }
}
