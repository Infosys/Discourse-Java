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

public class UserWarningsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWarningsDTO.class);
        UserWarningsDTO userWarningsDTO1 = new UserWarningsDTO();
        userWarningsDTO1.setId(1L);
        UserWarningsDTO userWarningsDTO2 = new UserWarningsDTO();
        assertThat(userWarningsDTO1).isNotEqualTo(userWarningsDTO2);
        userWarningsDTO2.setId(userWarningsDTO1.getId());
        assertThat(userWarningsDTO1).isEqualTo(userWarningsDTO2);
        userWarningsDTO2.setId(2L);
        assertThat(userWarningsDTO1).isNotEqualTo(userWarningsDTO2);
        userWarningsDTO1.setId(null);
        assertThat(userWarningsDTO1).isNotEqualTo(userWarningsDTO2);
    }
}
