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

public class TextClassificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextClassificationDTO.class);
        TextClassificationDTO textClassificationDTO1 = new TextClassificationDTO();
        textClassificationDTO1.setId(1L);
        TextClassificationDTO textClassificationDTO2 = new TextClassificationDTO();
        assertThat(textClassificationDTO1).isNotEqualTo(textClassificationDTO2);
        textClassificationDTO2.setId(textClassificationDTO1.getId());
        assertThat(textClassificationDTO1).isEqualTo(textClassificationDTO2);
        textClassificationDTO2.setId(2L);
        assertThat(textClassificationDTO1).isNotEqualTo(textClassificationDTO2);
        textClassificationDTO1.setId(null);
        assertThat(textClassificationDTO1).isNotEqualTo(textClassificationDTO2);
    }
}
