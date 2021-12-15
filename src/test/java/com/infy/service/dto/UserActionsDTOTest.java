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

public class UserActionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActionsDTO.class);
        UserActionsDTO userActionsDTO1 = new UserActionsDTO();
        userActionsDTO1.setId(1L);
        UserActionsDTO userActionsDTO2 = new UserActionsDTO();
        assertThat(userActionsDTO1).isNotEqualTo(userActionsDTO2);
        userActionsDTO2.setId(userActionsDTO1.getId());
        assertThat(userActionsDTO1).isEqualTo(userActionsDTO2);
        userActionsDTO2.setId(2L);
        assertThat(userActionsDTO1).isNotEqualTo(userActionsDTO2);
        userActionsDTO1.setId(null);
        assertThat(userActionsDTO1).isNotEqualTo(userActionsDTO2);
    }
}
