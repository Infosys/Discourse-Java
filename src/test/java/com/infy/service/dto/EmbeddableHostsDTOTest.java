/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class EmbeddableHostsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmbeddableHostsDTO.class);
        EmbeddableHostsDTO embeddableHostsDTO1 = new EmbeddableHostsDTO();
        embeddableHostsDTO1.setId(1L);
        EmbeddableHostsDTO embeddableHostsDTO2 = new EmbeddableHostsDTO();
        assertThat(embeddableHostsDTO1).isNotEqualTo(embeddableHostsDTO2);
        embeddableHostsDTO2.setId(embeddableHostsDTO1.getId());
        assertThat(embeddableHostsDTO1).isEqualTo(embeddableHostsDTO2);
        embeddableHostsDTO2.setId(2L);
        assertThat(embeddableHostsDTO1).isNotEqualTo(embeddableHostsDTO2);
        embeddableHostsDTO1.setId(null);
        assertThat(embeddableHostsDTO1).isNotEqualTo(embeddableHostsDTO2);
    }
}
