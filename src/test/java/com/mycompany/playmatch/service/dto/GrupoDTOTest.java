package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoDTO.class);
        GrupoDTO grupoDTO1 = new GrupoDTO();
        grupoDTO1.setId("id1");
        GrupoDTO grupoDTO2 = new GrupoDTO();
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
        grupoDTO2.setId(grupoDTO1.getId());
        assertThat(grupoDTO1).isEqualTo(grupoDTO2);
        grupoDTO2.setId("id2");
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
        grupoDTO1.setId(null);
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
    }
}
