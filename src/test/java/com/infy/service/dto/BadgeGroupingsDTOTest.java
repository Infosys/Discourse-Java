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

public class BadgeGroupingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeGroupingsDTO.class);
        BadgeGroupingsDTO badgeGroupingsDTO1 = new BadgeGroupingsDTO();
        badgeGroupingsDTO1.setId(1L);
        BadgeGroupingsDTO badgeGroupingsDTO2 = new BadgeGroupingsDTO();
        assertThat(badgeGroupingsDTO1).isNotEqualTo(badgeGroupingsDTO2);
        badgeGroupingsDTO2.setId(badgeGroupingsDTO1.getId());
        assertThat(badgeGroupingsDTO1).isEqualTo(badgeGroupingsDTO2);
        badgeGroupingsDTO2.setId(2L);
        assertThat(badgeGroupingsDTO1).isNotEqualTo(badgeGroupingsDTO2);
        badgeGroupingsDTO1.setId(null);
        assertThat(badgeGroupingsDTO1).isNotEqualTo(badgeGroupingsDTO2);
    }
}
