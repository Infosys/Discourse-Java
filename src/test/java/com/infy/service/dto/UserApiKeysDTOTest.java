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

public class UserApiKeysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApiKeysDTO.class);
        UserApiKeysDTO userApiKeysDTO1 = new UserApiKeysDTO();
        userApiKeysDTO1.setId(1L);
        UserApiKeysDTO userApiKeysDTO2 = new UserApiKeysDTO();
        assertThat(userApiKeysDTO1).isNotEqualTo(userApiKeysDTO2);
        userApiKeysDTO2.setId(userApiKeysDTO1.getId());
        assertThat(userApiKeysDTO1).isEqualTo(userApiKeysDTO2);
        userApiKeysDTO2.setId(2L);
        assertThat(userApiKeysDTO1).isNotEqualTo(userApiKeysDTO2);
        userApiKeysDTO1.setId(null);
        assertThat(userApiKeysDTO1).isNotEqualTo(userApiKeysDTO2);
    }
}
