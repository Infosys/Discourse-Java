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

public class UserSecurityKeysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSecurityKeysDTO.class);
        UserSecurityKeysDTO userSecurityKeysDTO1 = new UserSecurityKeysDTO();
        userSecurityKeysDTO1.setId(1L);
        UserSecurityKeysDTO userSecurityKeysDTO2 = new UserSecurityKeysDTO();
        assertThat(userSecurityKeysDTO1).isNotEqualTo(userSecurityKeysDTO2);
        userSecurityKeysDTO2.setId(userSecurityKeysDTO1.getId());
        assertThat(userSecurityKeysDTO1).isEqualTo(userSecurityKeysDTO2);
        userSecurityKeysDTO2.setId(2L);
        assertThat(userSecurityKeysDTO1).isNotEqualTo(userSecurityKeysDTO2);
        userSecurityKeysDTO1.setId(null);
        assertThat(userSecurityKeysDTO1).isNotEqualTo(userSecurityKeysDTO2);
    }
}
