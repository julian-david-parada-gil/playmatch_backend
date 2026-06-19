package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TorneoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TorneoDTO.class);
        TorneoDTO torneoDTO1 = new TorneoDTO();
        torneoDTO1.setId("id1");
        TorneoDTO torneoDTO2 = new TorneoDTO();
        assertThat(torneoDTO1).isNotEqualTo(torneoDTO2);
        torneoDTO2.setId(torneoDTO1.getId());
        assertThat(torneoDTO1).isEqualTo(torneoDTO2);
        torneoDTO2.setId("id2");
        assertThat(torneoDTO1).isNotEqualTo(torneoDTO2);
        torneoDTO1.setId(null);
        assertThat(torneoDTO1).isNotEqualTo(torneoDTO2);
    }
}
