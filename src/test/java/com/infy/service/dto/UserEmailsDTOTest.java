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

public class UserEmailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserEmailsDTO.class);
        UserEmailsDTO userEmailsDTO1 = new UserEmailsDTO();
        userEmailsDTO1.setId(1L);
        UserEmailsDTO userEmailsDTO2 = new UserEmailsDTO();
        assertThat(userEmailsDTO1).isNotEqualTo(userEmailsDTO2);
        userEmailsDTO2.setId(userEmailsDTO1.getId());
        assertThat(userEmailsDTO1).isEqualTo(userEmailsDTO2);
        userEmailsDTO2.setId(2L);
        assertThat(userEmailsDTO1).isNotEqualTo(userEmailsDTO2);
        userEmailsDTO1.setId(null);
        assertThat(userEmailsDTO1).isNotEqualTo(userEmailsDTO2);
    }
}
