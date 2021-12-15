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

public class UserApiKeyScopesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApiKeyScopesDTO.class);
        UserApiKeyScopesDTO userApiKeyScopesDTO1 = new UserApiKeyScopesDTO();
        userApiKeyScopesDTO1.setId(1L);
        UserApiKeyScopesDTO userApiKeyScopesDTO2 = new UserApiKeyScopesDTO();
        assertThat(userApiKeyScopesDTO1).isNotEqualTo(userApiKeyScopesDTO2);
        userApiKeyScopesDTO2.setId(userApiKeyScopesDTO1.getId());
        assertThat(userApiKeyScopesDTO1).isEqualTo(userApiKeyScopesDTO2);
        userApiKeyScopesDTO2.setId(2L);
        assertThat(userApiKeyScopesDTO1).isNotEqualTo(userApiKeyScopesDTO2);
        userApiKeyScopesDTO1.setId(null);
        assertThat(userApiKeyScopesDTO1).isNotEqualTo(userApiKeyScopesDTO2);
    }
}
