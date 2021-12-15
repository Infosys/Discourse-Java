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

public class TopTopicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopTopicsDTO.class);
        TopTopicsDTO topTopicsDTO1 = new TopTopicsDTO();
        topTopicsDTO1.setId(1L);
        TopTopicsDTO topTopicsDTO2 = new TopTopicsDTO();
        assertThat(topTopicsDTO1).isNotEqualTo(topTopicsDTO2);
        topTopicsDTO2.setId(topTopicsDTO1.getId());
        assertThat(topTopicsDTO1).isEqualTo(topTopicsDTO2);
        topTopicsDTO2.setId(2L);
        assertThat(topTopicsDTO1).isNotEqualTo(topTopicsDTO2);
        topTopicsDTO1.setId(null);
        assertThat(topTopicsDTO1).isNotEqualTo(topTopicsDTO2);
    }
}
