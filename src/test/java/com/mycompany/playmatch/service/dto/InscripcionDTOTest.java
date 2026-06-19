package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscripcionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscripcionDTO.class);
        InscripcionDTO inscripcionDTO1 = new InscripcionDTO();
        inscripcionDTO1.setId("id1");
        InscripcionDTO inscripcionDTO2 = new InscripcionDTO();
        assertThat(inscripcionDTO1).isNotEqualTo(inscripcionDTO2);
        inscripcionDTO2.setId(inscripcionDTO1.getId());
        assertThat(inscripcionDTO1).isEqualTo(inscripcionDTO2);
        inscripcionDTO2.setId("id2");
        assertThat(inscripcionDTO1).isNotEqualTo(inscripcionDTO2);
        inscripcionDTO1.setId(null);
        assertThat(inscripcionDTO1).isNotEqualTo(inscripcionDTO2);
    }
}
