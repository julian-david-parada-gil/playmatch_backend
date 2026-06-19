package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TablaPosicionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablaPosicionDTO.class);
        TablaPosicionDTO tablaPosicionDTO1 = new TablaPosicionDTO();
        tablaPosicionDTO1.setId("id1");
        TablaPosicionDTO tablaPosicionDTO2 = new TablaPosicionDTO();
        assertThat(tablaPosicionDTO1).isNotEqualTo(tablaPosicionDTO2);
        tablaPosicionDTO2.setId(tablaPosicionDTO1.getId());
        assertThat(tablaPosicionDTO1).isEqualTo(tablaPosicionDTO2);
        tablaPosicionDTO2.setId("id2");
        assertThat(tablaPosicionDTO1).isNotEqualTo(tablaPosicionDTO2);
        tablaPosicionDTO1.setId(null);
        assertThat(tablaPosicionDTO1).isNotEqualTo(tablaPosicionDTO2);
    }
}
