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

public class UserFieldOptionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFieldOptionsDTO.class);
        UserFieldOptionsDTO userFieldOptionsDTO1 = new UserFieldOptionsDTO();
        userFieldOptionsDTO1.setId(1L);
        UserFieldOptionsDTO userFieldOptionsDTO2 = new UserFieldOptionsDTO();
        assertThat(userFieldOptionsDTO1).isNotEqualTo(userFieldOptionsDTO2);
        userFieldOptionsDTO2.setId(userFieldOptionsDTO1.getId());
        assertThat(userFieldOptionsDTO1).isEqualTo(userFieldOptionsDTO2);
        userFieldOptionsDTO2.setId(2L);
        assertThat(userFieldOptionsDTO1).isNotEqualTo(userFieldOptionsDTO2);
        userFieldOptionsDTO1.setId(null);
        assertThat(userFieldOptionsDTO1).isNotEqualTo(userFieldOptionsDTO2);
    }
}
