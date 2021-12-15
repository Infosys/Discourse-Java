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

public class UserAuthTokensDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAuthTokensDTO.class);
        UserAuthTokensDTO userAuthTokensDTO1 = new UserAuthTokensDTO();
        userAuthTokensDTO1.setId(1L);
        UserAuthTokensDTO userAuthTokensDTO2 = new UserAuthTokensDTO();
        assertThat(userAuthTokensDTO1).isNotEqualTo(userAuthTokensDTO2);
        userAuthTokensDTO2.setId(userAuthTokensDTO1.getId());
        assertThat(userAuthTokensDTO1).isEqualTo(userAuthTokensDTO2);
        userAuthTokensDTO2.setId(2L);
        assertThat(userAuthTokensDTO1).isNotEqualTo(userAuthTokensDTO2);
        userAuthTokensDTO1.setId(null);
        assertThat(userAuthTokensDTO1).isNotEqualTo(userAuthTokensDTO2);
    }
}
