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

public class UserSearchDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSearchDataDTO.class);
        UserSearchDataDTO userSearchDataDTO1 = new UserSearchDataDTO();
        userSearchDataDTO1.setId(1L);
        UserSearchDataDTO userSearchDataDTO2 = new UserSearchDataDTO();
        assertThat(userSearchDataDTO1).isNotEqualTo(userSearchDataDTO2);
        userSearchDataDTO2.setId(userSearchDataDTO1.getId());
        assertThat(userSearchDataDTO1).isEqualTo(userSearchDataDTO2);
        userSearchDataDTO2.setId(2L);
        assertThat(userSearchDataDTO1).isNotEqualTo(userSearchDataDTO2);
        userSearchDataDTO1.setId(null);
        assertThat(userSearchDataDTO1).isNotEqualTo(userSearchDataDTO2);
    }
}
