package com.mycompany.playmatch.domain;

import java.util.UUID;

public class InscripcionTestSamples {

    public static Inscripcion getInscripcionSample1() {
        return new Inscripcion().id("id1").codigo("codigo1");
    }

    public static Inscripcion getInscripcionSample2() {
        return new Inscripcion().id("id2").codigo("codigo2");
    }

    public static Inscripcion getInscripcionRandomSampleGenerator() {
        return new Inscripcion().id(UUID.randomUUID().toString()).codigo(UUID.randomUUID().toString());
    }
}
