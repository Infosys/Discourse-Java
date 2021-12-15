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

public class UserExportsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExportsDTO.class);
        UserExportsDTO userExportsDTO1 = new UserExportsDTO();
        userExportsDTO1.setId(1L);
        UserExportsDTO userExportsDTO2 = new UserExportsDTO();
        assertThat(userExportsDTO1).isNotEqualTo(userExportsDTO2);
        userExportsDTO2.setId(userExportsDTO1.getId());
        assertThat(userExportsDTO1).isEqualTo(userExportsDTO2);
        userExportsDTO2.setId(2L);
        assertThat(userExportsDTO1).isNotEqualTo(userExportsDTO2);
        userExportsDTO1.setId(null);
        assertThat(userExportsDTO1).isNotEqualTo(userExportsDTO2);
    }
}
