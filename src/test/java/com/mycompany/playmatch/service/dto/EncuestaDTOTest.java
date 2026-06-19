package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EncuestaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncuestaDTO.class);
        EncuestaDTO encuestaDTO1 = new EncuestaDTO();
        encuestaDTO1.setId("id1");
        EncuestaDTO encuestaDTO2 = new EncuestaDTO();
        assertThat(encuestaDTO1).isNotEqualTo(encuestaDTO2);
        encuestaDTO2.setId(encuestaDTO1.getId());
        assertThat(encuestaDTO1).isEqualTo(encuestaDTO2);
        encuestaDTO2.setId("id2");
        assertThat(encuestaDTO1).isNotEqualTo(encuestaDTO2);
        encuestaDTO1.setId(null);
        assertThat(encuestaDTO1).isNotEqualTo(encuestaDTO2);
    }
}
