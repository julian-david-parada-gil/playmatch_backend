package com.mycompany.playmatch.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TablaPosicionTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TablaPosicion getTablaPosicionSample1() {
        return new TablaPosicion()
            .id("id1")
            .puntos(1)
            .partidosJugados(1)
            .partidosGanados(1)
            .partidosEmpatados(1)
            .partidosPerdidos(1)
            .golesFavor(1)
            .golesContra(1)
            .diferenciaGoles(1);
    }

    public static TablaPosicion getTablaPosicionSample2() {
        return new TablaPosicion()
            .id("id2")
            .puntos(2)
            .partidosJugados(2)
            .partidosGanados(2)
            .partidosEmpatados(2)
            .partidosPerdidos(2)
            .golesFavor(2)
            .golesContra(2)
            .diferenciaGoles(2);
    }

    public static TablaPosicion getTablaPosicionRandomSampleGenerator() {
        return new TablaPosicion()
            .id(UUID.randomUUID().toString())
            .puntos(intCount.incrementAndGet())
            .partidosJugados(intCount.incrementAndGet())
            .partidosGanados(intCount.incrementAndGet())
            .partidosEmpatados(intCount.incrementAndGet())
            .partidosPerdidos(intCount.incrementAndGet())
            .golesFavor(intCount.incrementAndGet())
            .golesContra(intCount.incrementAndGet())
            .diferenciaGoles(intCount.incrementAndGet());
    }
}
