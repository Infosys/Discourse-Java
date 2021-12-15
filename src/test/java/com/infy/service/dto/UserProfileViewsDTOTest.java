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

public class UserProfileViewsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfileViewsDTO.class);
        UserProfileViewsDTO userProfileViewsDTO1 = new UserProfileViewsDTO();
        userProfileViewsDTO1.setId(1L);
        UserProfileViewsDTO userProfileViewsDTO2 = new UserProfileViewsDTO();
        assertThat(userProfileViewsDTO1).isNotEqualTo(userProfileViewsDTO2);
        userProfileViewsDTO2.setId(userProfileViewsDTO1.getId());
        assertThat(userProfileViewsDTO1).isEqualTo(userProfileViewsDTO2);
        userProfileViewsDTO2.setId(2L);
        assertThat(userProfileViewsDTO1).isNotEqualTo(userProfileViewsDTO2);
        userProfileViewsDTO1.setId(null);
        assertThat(userProfileViewsDTO1).isNotEqualTo(userProfileViewsDTO2);
    }
}
