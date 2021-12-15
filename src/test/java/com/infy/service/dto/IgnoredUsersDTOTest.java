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

public class IgnoredUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IgnoredUsersDTO.class);
        IgnoredUsersDTO ignoredUsersDTO1 = new IgnoredUsersDTO();
        ignoredUsersDTO1.setId(1L);
        IgnoredUsersDTO ignoredUsersDTO2 = new IgnoredUsersDTO();
        assertThat(ignoredUsersDTO1).isNotEqualTo(ignoredUsersDTO2);
        ignoredUsersDTO2.setId(ignoredUsersDTO1.getId());
        assertThat(ignoredUsersDTO1).isEqualTo(ignoredUsersDTO2);
        ignoredUsersDTO2.setId(2L);
        assertThat(ignoredUsersDTO1).isNotEqualTo(ignoredUsersDTO2);
        ignoredUsersDTO1.setId(null);
        assertThat(ignoredUsersDTO1).isNotEqualTo(ignoredUsersDTO2);
    }
}
