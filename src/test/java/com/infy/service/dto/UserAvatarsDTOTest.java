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

public class UserAvatarsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAvatarsDTO.class);
        UserAvatarsDTO userAvatarsDTO1 = new UserAvatarsDTO();
        userAvatarsDTO1.setId(1L);
        UserAvatarsDTO userAvatarsDTO2 = new UserAvatarsDTO();
        assertThat(userAvatarsDTO1).isNotEqualTo(userAvatarsDTO2);
        userAvatarsDTO2.setId(userAvatarsDTO1.getId());
        assertThat(userAvatarsDTO1).isEqualTo(userAvatarsDTO2);
        userAvatarsDTO2.setId(2L);
        assertThat(userAvatarsDTO1).isNotEqualTo(userAvatarsDTO2);
        userAvatarsDTO1.setId(null);
        assertThat(userAvatarsDTO1).isNotEqualTo(userAvatarsDTO2);
    }
}
