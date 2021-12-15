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

public class GroupCustomFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCustomFieldsDTO.class);
        GroupCustomFieldsDTO groupCustomFieldsDTO1 = new GroupCustomFieldsDTO();
        groupCustomFieldsDTO1.setId(1L);
        GroupCustomFieldsDTO groupCustomFieldsDTO2 = new GroupCustomFieldsDTO();
        assertThat(groupCustomFieldsDTO1).isNotEqualTo(groupCustomFieldsDTO2);
        groupCustomFieldsDTO2.setId(groupCustomFieldsDTO1.getId());
        assertThat(groupCustomFieldsDTO1).isEqualTo(groupCustomFieldsDTO2);
        groupCustomFieldsDTO2.setId(2L);
        assertThat(groupCustomFieldsDTO1).isNotEqualTo(groupCustomFieldsDTO2);
        groupCustomFieldsDTO1.setId(null);
        assertThat(groupCustomFieldsDTO1).isNotEqualTo(groupCustomFieldsDTO2);
    }
}
