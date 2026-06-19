package com.mycompany.playmatch.domain;

import static com.mycompany.playmatch.domain.CategoriaTestSamples.*;
import static com.mycompany.playmatch.domain.TorneoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categoria.class);
        Categoria categoria1 = getCategoriaSample1();
        Categoria categoria2 = new Categoria();
        assertThat(categoria1).isNotEqualTo(categoria2);

        categoria2.setId(categoria1.getId());
        assertThat(categoria1).isEqualTo(categoria2);

        categoria2 = getCategoriaSample2();
        assertThat(categoria1).isNotEqualTo(categoria2);
    }

    @Test
    void torneosTest() {
        Categoria categoria = getCategoriaRandomSampleGenerator();
        Torneo torneoBack = getTorneoRandomSampleGenerator();

        categoria.addTorneos(torneoBack);
        assertThat(categoria.getTorneoses()).containsOnly(torneoBack);
        assertThat(torneoBack.getCategoria()).isEqualTo(categoria);

        categoria.removeTorneos(torneoBack);
        assertThat(categoria.getTorneoses()).doesNotContain(torneoBack);
        assertThat(torneoBack.getCategoria()).isNull();

        categoria.torneoses(new HashSet<>(Set.of(torneoBack)));
        assertThat(categoria.getTorneoses()).containsOnly(torneoBack);
        assertThat(torneoBack.getCategoria()).isEqualTo(categoria);

        categoria.setTorneoses(new HashSet<>());
        assertThat(categoria.getTorneoses()).doesNotContain(torneoBack);
        assertThat(torneoBack.getCategoria()).isNull();
    }
}
