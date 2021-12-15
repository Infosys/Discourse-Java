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

public class BadgesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgesDTO.class);
        BadgesDTO badgesDTO1 = new BadgesDTO();
        badgesDTO1.setId(1L);
        BadgesDTO badgesDTO2 = new BadgesDTO();
        assertThat(badgesDTO1).isNotEqualTo(badgesDTO2);
        badgesDTO2.setId(badgesDTO1.getId());
        assertThat(badgesDTO1).isEqualTo(badgesDTO2);
        badgesDTO2.setId(2L);
        assertThat(badgesDTO1).isNotEqualTo(badgesDTO2);
        badgesDTO1.setId(null);
        assertThat(badgesDTO1).isNotEqualTo(badgesDTO2);
    }
}
