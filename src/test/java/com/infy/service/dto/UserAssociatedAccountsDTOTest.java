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

public class UserAssociatedAccountsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAssociatedAccountsDTO.class);
        UserAssociatedAccountsDTO userAssociatedAccountsDTO1 = new UserAssociatedAccountsDTO();
        userAssociatedAccountsDTO1.setId(1L);
        UserAssociatedAccountsDTO userAssociatedAccountsDTO2 = new UserAssociatedAccountsDTO();
        assertThat(userAssociatedAccountsDTO1).isNotEqualTo(userAssociatedAccountsDTO2);
        userAssociatedAccountsDTO2.setId(userAssociatedAccountsDTO1.getId());
        assertThat(userAssociatedAccountsDTO1).isEqualTo(userAssociatedAccountsDTO2);
        userAssociatedAccountsDTO2.setId(2L);
        assertThat(userAssociatedAccountsDTO1).isNotEqualTo(userAssociatedAccountsDTO2);
        userAssociatedAccountsDTO1.setId(null);
        assertThat(userAssociatedAccountsDTO1).isNotEqualTo(userAssociatedAccountsDTO2);
    }
}
