package com.mycompany.playmatch.domain;

import java.util.UUID;

public class NoticiaTestSamples {

    public static Noticia getNoticiaSample1() {
        return new Noticia().id("id1").titulo("titulo1");
    }

    public static Noticia getNoticiaSample2() {
        return new Noticia().id("id2").titulo("titulo2");
    }

    public static Noticia getNoticiaRandomSampleGenerator() {
        return new Noticia().id(UUID.randomUUID().toString()).titulo(UUID.randomUUID().toString());
    }
}
