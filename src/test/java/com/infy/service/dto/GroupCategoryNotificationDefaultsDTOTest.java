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

public class GroupCategoryNotificationDefaultsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCategoryNotificationDefaultsDTO.class);
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO1 = new GroupCategoryNotificationDefaultsDTO();
        groupCategoryNotificationDefaultsDTO1.setId(1L);
        GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO2 = new GroupCategoryNotificationDefaultsDTO();
        assertThat(groupCategoryNotificationDefaultsDTO1).isNotEqualTo(groupCategoryNotificationDefaultsDTO2);
        groupCategoryNotificationDefaultsDTO2.setId(groupCategoryNotificationDefaultsDTO1.getId());
        assertThat(groupCategoryNotificationDefaultsDTO1).isEqualTo(groupCategoryNotificationDefaultsDTO2);
        groupCategoryNotificationDefaultsDTO2.setId(2L);
        assertThat(groupCategoryNotificationDefaultsDTO1).isNotEqualTo(groupCategoryNotificationDefaultsDTO2);
        groupCategoryNotificationDefaultsDTO1.setId(null);
        assertThat(groupCategoryNotificationDefaultsDTO1).isNotEqualTo(groupCategoryNotificationDefaultsDTO2);
    }
}
