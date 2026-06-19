package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalificacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalificacionDTO.class);
        CalificacionDTO calificacionDTO1 = new CalificacionDTO();
        calificacionDTO1.setId("id1");
        CalificacionDTO calificacionDTO2 = new CalificacionDTO();
        assertThat(calificacionDTO1).isNotEqualTo(calificacionDTO2);
        calificacionDTO2.setId(calificacionDTO1.getId());
        assertThat(calificacionDTO1).isEqualTo(calificacionDTO2);
        calificacionDTO2.setId("id2");
        assertThat(calificacionDTO1).isNotEqualTo(calificacionDTO2);
        calificacionDTO1.setId(null);
        assertThat(calificacionDTO1).isNotEqualTo(calificacionDTO2);
    }
}
