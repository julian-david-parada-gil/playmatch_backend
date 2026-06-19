package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MiembroGrupoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiembroGrupoDTO.class);
        MiembroGrupoDTO miembroGrupoDTO1 = new MiembroGrupoDTO();
        miembroGrupoDTO1.setId("id1");
        MiembroGrupoDTO miembroGrupoDTO2 = new MiembroGrupoDTO();
        assertThat(miembroGrupoDTO1).isNotEqualTo(miembroGrupoDTO2);
        miembroGrupoDTO2.setId(miembroGrupoDTO1.getId());
        assertThat(miembroGrupoDTO1).isEqualTo(miembroGrupoDTO2);
        miembroGrupoDTO2.setId("id2");
        assertThat(miembroGrupoDTO1).isNotEqualTo(miembroGrupoDTO2);
        miembroGrupoDTO1.setId(null);
        assertThat(miembroGrupoDTO1).isNotEqualTo(miembroGrupoDTO2);
    }
}
