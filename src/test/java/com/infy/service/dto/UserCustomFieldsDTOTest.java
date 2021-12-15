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

public class UserCustomFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCustomFieldsDTO.class);
        UserCustomFieldsDTO userCustomFieldsDTO1 = new UserCustomFieldsDTO();
        userCustomFieldsDTO1.setId(1L);
        UserCustomFieldsDTO userCustomFieldsDTO2 = new UserCustomFieldsDTO();
        assertThat(userCustomFieldsDTO1).isNotEqualTo(userCustomFieldsDTO2);
        userCustomFieldsDTO2.setId(userCustomFieldsDTO1.getId());
        assertThat(userCustomFieldsDTO1).isEqualTo(userCustomFieldsDTO2);
        userCustomFieldsDTO2.setId(2L);
        assertThat(userCustomFieldsDTO1).isNotEqualTo(userCustomFieldsDTO2);
        userCustomFieldsDTO1.setId(null);
        assertThat(userCustomFieldsDTO1).isNotEqualTo(userCustomFieldsDTO2);
    }
}
