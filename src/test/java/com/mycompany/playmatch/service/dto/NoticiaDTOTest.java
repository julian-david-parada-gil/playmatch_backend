package com.mycompany.playmatch.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.playmatch.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticiaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticiaDTO.class);
        NoticiaDTO noticiaDTO1 = new NoticiaDTO();
        noticiaDTO1.setId("id1");
        NoticiaDTO noticiaDTO2 = new NoticiaDTO();
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
        noticiaDTO2.setId(noticiaDTO1.getId());
        assertThat(noticiaDTO1).isEqualTo(noticiaDTO2);
        noticiaDTO2.setId("id2");
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
        noticiaDTO1.setId(null);
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
    }
}
