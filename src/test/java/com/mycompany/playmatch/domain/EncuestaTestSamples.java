package com.mycompany.playmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EncuestaTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Encuesta getEncuestaSample1() {
        return new Encuesta()
            .id("id1")
            .opcion1("opcion11")
            .opcion2("opcion21")
            .opcion3("opcion31")
            .opcion4("opcion41")
            .votosOpcion1(1)
            .votosOpcion2(1)
            .votosOpcion3(1)
            .votosOpcion4(1);
    }

    public static Encuesta getEncuestaSample2() {
        return new Encuesta()
            .id("id2")
            .opcion1("opcion12")
            .opcion2("opcion22")
            .opcion3("opcion32")
            .opcion4("opcion42")
            .votosOpcion1(2)
            .votosOpcion2(2)
            .votosOpcion3(2)
            .votosOpcion4(2);
    }

    public static Encuesta getEncuestaRandomSampleGenerator() {
        return new Encuesta()
            .id(UUID.randomUUID().toString())
            .opcion1(UUID.randomUUID().toString())
            .opcion2(UUID.randomUUID().toString())
            .opcion3(UUID.randomUUID().toString())
            .opcion4(UUID.randomUUID().toString())
            .votosOpcion1(intCount.incrementAndGet())
            .votosOpcion2(intCount.incrementAndGet())
            .votosOpcion3(intCount.incrementAndGet())
            .votosOpcion4(intCount.incrementAndGet());
    }
}
