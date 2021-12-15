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

public class UserAuthTokenLogsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAuthTokenLogsDTO.class);
        UserAuthTokenLogsDTO userAuthTokenLogsDTO1 = new UserAuthTokenLogsDTO();
        userAuthTokenLogsDTO1.setId(1L);
        UserAuthTokenLogsDTO userAuthTokenLogsDTO2 = new UserAuthTokenLogsDTO();
        assertThat(userAuthTokenLogsDTO1).isNotEqualTo(userAuthTokenLogsDTO2);
        userAuthTokenLogsDTO2.setId(userAuthTokenLogsDTO1.getId());
        assertThat(userAuthTokenLogsDTO1).isEqualTo(userAuthTokenLogsDTO2);
        userAuthTokenLogsDTO2.setId(2L);
        assertThat(userAuthTokenLogsDTO1).isNotEqualTo(userAuthTokenLogsDTO2);
        userAuthTokenLogsDTO1.setId(null);
        assertThat(userAuthTokenLogsDTO1).isNotEqualTo(userAuthTokenLogsDTO2);
    }
}
