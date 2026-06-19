package com.mycompany.playmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ConvocatoriaTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Convocatoria getConvocatoriaSample1() {
        return new Convocatoria().id("id1").titulo("titulo1").cupos(1);
    }

    public static Convocatoria getConvocatoriaSample2() {
        return new Convocatoria().id("id2").titulo("titulo2").cupos(2);
    }

    public static Convocatoria getConvocatoriaRandomSampleGenerator() {
        return new Convocatoria().id(UUID.randomUUID().toString()).titulo(UUID.randomUUID().toString()).cupos(intCount.incrementAndGet());
    }
}
