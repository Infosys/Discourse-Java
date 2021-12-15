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

public class BadgeTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeTypesDTO.class);
        BadgeTypesDTO badgeTypesDTO1 = new BadgeTypesDTO();
        badgeTypesDTO1.setId(1L);
        BadgeTypesDTO badgeTypesDTO2 = new BadgeTypesDTO();
        assertThat(badgeTypesDTO1).isNotEqualTo(badgeTypesDTO2);
        badgeTypesDTO2.setId(badgeTypesDTO1.getId());
        assertThat(badgeTypesDTO1).isEqualTo(badgeTypesDTO2);
        badgeTypesDTO2.setId(2L);
        assertThat(badgeTypesDTO1).isNotEqualTo(badgeTypesDTO2);
        badgeTypesDTO1.setId(null);
        assertThat(badgeTypesDTO1).isNotEqualTo(badgeTypesDTO2);
    }
}
