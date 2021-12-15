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

public class UserOpenIdsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOpenIdsDTO.class);
        UserOpenIdsDTO userOpenIdsDTO1 = new UserOpenIdsDTO();
        userOpenIdsDTO1.setId(1L);
        UserOpenIdsDTO userOpenIdsDTO2 = new UserOpenIdsDTO();
        assertThat(userOpenIdsDTO1).isNotEqualTo(userOpenIdsDTO2);
        userOpenIdsDTO2.setId(userOpenIdsDTO1.getId());
        assertThat(userOpenIdsDTO1).isEqualTo(userOpenIdsDTO2);
        userOpenIdsDTO2.setId(2L);
        assertThat(userOpenIdsDTO1).isNotEqualTo(userOpenIdsDTO2);
        userOpenIdsDTO1.setId(null);
        assertThat(userOpenIdsDTO1).isNotEqualTo(userOpenIdsDTO2);
    }
}
