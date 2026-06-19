package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalendarioEventoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarioEventoDTO.class);
        CalendarioEventoDTO calendarioEventoDTO1 = new CalendarioEventoDTO();
        calendarioEventoDTO1.setId("id1");
        CalendarioEventoDTO calendarioEventoDTO2 = new CalendarioEventoDTO();
        assertThat(calendarioEventoDTO1).isNotEqualTo(calendarioEventoDTO2);
        calendarioEventoDTO2.setId(calendarioEventoDTO1.getId());
        assertThat(calendarioEventoDTO1).isEqualTo(calendarioEventoDTO2);
        calendarioEventoDTO2.setId("id2");
        assertThat(calendarioEventoDTO1).isNotEqualTo(calendarioEventoDTO2);
        calendarioEventoDTO1.setId(null);
        assertThat(calendarioEventoDTO1).isNotEqualTo(calendarioEventoDTO2);
    }
}
