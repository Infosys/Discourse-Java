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

public class UserBadgesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBadgesDTO.class);
        UserBadgesDTO userBadgesDTO1 = new UserBadgesDTO();
        userBadgesDTO1.setId(1L);
        UserBadgesDTO userBadgesDTO2 = new UserBadgesDTO();
        assertThat(userBadgesDTO1).isNotEqualTo(userBadgesDTO2);
        userBadgesDTO2.setId(userBadgesDTO1.getId());
        assertThat(userBadgesDTO1).isEqualTo(userBadgesDTO2);
        userBadgesDTO2.setId(2L);
        assertThat(userBadgesDTO1).isNotEqualTo(userBadgesDTO2);
        userBadgesDTO1.setId(null);
        assertThat(userBadgesDTO1).isNotEqualTo(userBadgesDTO2);
    }
}
