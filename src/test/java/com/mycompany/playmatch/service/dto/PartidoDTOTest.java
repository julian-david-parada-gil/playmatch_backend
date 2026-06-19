package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartidoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartidoDTO.class);
        PartidoDTO partidoDTO1 = new PartidoDTO();
        partidoDTO1.setId("id1");
        PartidoDTO partidoDTO2 = new PartidoDTO();
        assertThat(partidoDTO1).isNotEqualTo(partidoDTO2);
        partidoDTO2.setId(partidoDTO1.getId());
        assertThat(partidoDTO1).isEqualTo(partidoDTO2);
        partidoDTO2.setId("id2");
        assertThat(partidoDTO1).isNotEqualTo(partidoDTO2);
        partidoDTO1.setId(null);
        assertThat(partidoDTO1).isNotEqualTo(partidoDTO2);
    }
}
