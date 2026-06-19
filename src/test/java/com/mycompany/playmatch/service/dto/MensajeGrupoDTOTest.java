package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MensajeGrupoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensajeGrupoDTO.class);
        MensajeGrupoDTO mensajeGrupoDTO1 = new MensajeGrupoDTO();
        mensajeGrupoDTO1.setId("id1");
        MensajeGrupoDTO mensajeGrupoDTO2 = new MensajeGrupoDTO();
        assertThat(mensajeGrupoDTO1).isNotEqualTo(mensajeGrupoDTO2);
        mensajeGrupoDTO2.setId(mensajeGrupoDTO1.getId());
        assertThat(mensajeGrupoDTO1).isEqualTo(mensajeGrupoDTO2);
        mensajeGrupoDTO2.setId("id2");
        assertThat(mensajeGrupoDTO1).isNotEqualTo(mensajeGrupoDTO2);
        mensajeGrupoDTO1.setId(null);
        assertThat(mensajeGrupoDTO1).isNotEqualTo(mensajeGrupoDTO2);
    }
}
