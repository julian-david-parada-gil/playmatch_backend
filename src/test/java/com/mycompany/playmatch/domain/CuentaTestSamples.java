package com.mycompany.playmatch.domain;

import java.util.UUID;

public class CuentaTestSamples {

    public static Cuenta getCuentaSample1() {
        return new Cuenta().id("id1").numeroDocumento("numeroDocumento1").correo("correo1").telefono("telefono1").direccion("direccion1");
    }

    public static Cuenta getCuentaSample2() {
        return new Cuenta().id("id2").numeroDocumento("numeroDocumento2").correo("correo2").telefono("telefono2").direccion("direccion2");
    }

    public static Cuenta getCuentaRandomSampleGenerator() {
        return new Cuenta()
            .id(UUID.randomUUID().toString())
            .numeroDocumento(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString());
    }
}
