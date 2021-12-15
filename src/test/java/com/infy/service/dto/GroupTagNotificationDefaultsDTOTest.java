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

public class GroupTagNotificationDefaultsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupTagNotificationDefaultsDTO.class);
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO1 = new GroupTagNotificationDefaultsDTO();
        groupTagNotificationDefaultsDTO1.setId(1L);
        GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO2 = new GroupTagNotificationDefaultsDTO();
        assertThat(groupTagNotificationDefaultsDTO1).isNotEqualTo(groupTagNotificationDefaultsDTO2);
        groupTagNotificationDefaultsDTO2.setId(groupTagNotificationDefaultsDTO1.getId());
        assertThat(groupTagNotificationDefaultsDTO1).isEqualTo(groupTagNotificationDefaultsDTO2);
        groupTagNotificationDefaultsDTO2.setId(2L);
        assertThat(groupTagNotificationDefaultsDTO1).isNotEqualTo(groupTagNotificationDefaultsDTO2);
        groupTagNotificationDefaultsDTO1.setId(null);
        assertThat(groupTagNotificationDefaultsDTO1).isNotEqualTo(groupTagNotificationDefaultsDTO2);
    }
}
