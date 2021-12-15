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

public class UserOptionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOptionsDTO.class);
        UserOptionsDTO userOptionsDTO1 = new UserOptionsDTO();
        userOptionsDTO1.setId(1L);
        UserOptionsDTO userOptionsDTO2 = new UserOptionsDTO();
        assertThat(userOptionsDTO1).isNotEqualTo(userOptionsDTO2);
        userOptionsDTO2.setId(userOptionsDTO1.getId());
        assertThat(userOptionsDTO1).isEqualTo(userOptionsDTO2);
        userOptionsDTO2.setId(2L);
        assertThat(userOptionsDTO1).isNotEqualTo(userOptionsDTO2);
        userOptionsDTO1.setId(null);
        assertThat(userOptionsDTO1).isNotEqualTo(userOptionsDTO2);
    }
}
