package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificacionDTO.class);
        NotificacionDTO notificacionDTO1 = new NotificacionDTO();
        notificacionDTO1.setId("id1");
        NotificacionDTO notificacionDTO2 = new NotificacionDTO();
        assertThat(notificacionDTO1).isNotEqualTo(notificacionDTO2);
        notificacionDTO2.setId(notificacionDTO1.getId());
        assertThat(notificacionDTO1).isEqualTo(notificacionDTO2);
        notificacionDTO2.setId("id2");
        assertThat(notificacionDTO1).isNotEqualTo(notificacionDTO2);
        notificacionDTO1.setId(null);
        assertThat(notificacionDTO1).isNotEqualTo(notificacionDTO2);
    }
}
