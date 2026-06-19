package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CuentaTestSamples.*;
import static com.mycompany.playmatch.domain.NoticiaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noticia.class);
        Noticia noticia1 = getNoticiaSample1();
        Noticia noticia2 = new Noticia();
        assertThat(noticia1).isNotEqualTo(noticia2);

        noticia2.setId(noticia1.getId());
        assertThat(noticia1).isEqualTo(noticia2);

        noticia2 = getNoticiaSample2();
        assertThat(noticia1).isNotEqualTo(noticia2);
    }

    @Test
    void autorTest() {
        Noticia noticia = getNoticiaRandomSampleGenerator();
        Cuenta cuentaBack = getCuentaRandomSampleGenerator();

        noticia.setAutor(cuentaBack);
        assertThat(noticia.getAutor()).isEqualTo(cuentaBack);

        noticia.autor(null);
        assertThat(noticia.getAutor()).isNull();
    }
}
