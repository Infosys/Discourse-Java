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

public class UserFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFieldsDTO.class);
        UserFieldsDTO userFieldsDTO1 = new UserFieldsDTO();
        userFieldsDTO1.setId(1L);
        UserFieldsDTO userFieldsDTO2 = new UserFieldsDTO();
        assertThat(userFieldsDTO1).isNotEqualTo(userFieldsDTO2);
        userFieldsDTO2.setId(userFieldsDTO1.getId());
        assertThat(userFieldsDTO1).isEqualTo(userFieldsDTO2);
        userFieldsDTO2.setId(2L);
        assertThat(userFieldsDTO1).isNotEqualTo(userFieldsDTO2);
        userFieldsDTO1.setId(null);
        assertThat(userFieldsDTO1).isNotEqualTo(userFieldsDTO2);
    }
}
