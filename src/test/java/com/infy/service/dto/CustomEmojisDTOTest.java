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

public class CustomEmojisDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomEmojisDTO.class);
        CustomEmojisDTO customEmojisDTO1 = new CustomEmojisDTO();
        customEmojisDTO1.setId(1L);
        CustomEmojisDTO customEmojisDTO2 = new CustomEmojisDTO();
        assertThat(customEmojisDTO1).isNotEqualTo(customEmojisDTO2);
        customEmojisDTO2.setId(customEmojisDTO1.getId());
        assertThat(customEmojisDTO1).isEqualTo(customEmojisDTO2);
        customEmojisDTO2.setId(2L);
        assertThat(customEmojisDTO1).isNotEqualTo(customEmojisDTO2);
        customEmojisDTO1.setId(null);
        assertThat(customEmojisDTO1).isNotEqualTo(customEmojisDTO2);
    }
}
