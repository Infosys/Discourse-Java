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

public class LinkedTopicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkedTopicsDTO.class);
        LinkedTopicsDTO linkedTopicsDTO1 = new LinkedTopicsDTO();
        linkedTopicsDTO1.setId(1L);
        LinkedTopicsDTO linkedTopicsDTO2 = new LinkedTopicsDTO();
        assertThat(linkedTopicsDTO1).isNotEqualTo(linkedTopicsDTO2);
        linkedTopicsDTO2.setId(linkedTopicsDTO1.getId());
        assertThat(linkedTopicsDTO1).isEqualTo(linkedTopicsDTO2);
        linkedTopicsDTO2.setId(2L);
        assertThat(linkedTopicsDTO1).isNotEqualTo(linkedTopicsDTO2);
        linkedTopicsDTO1.setId(null);
        assertThat(linkedTopicsDTO1).isNotEqualTo(linkedTopicsDTO2);
    }
}
