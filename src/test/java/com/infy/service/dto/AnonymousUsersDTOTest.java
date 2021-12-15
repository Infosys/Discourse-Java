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

public class AnonymousUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnonymousUsersDTO.class);
        AnonymousUsersDTO anonymousUsersDTO1 = new AnonymousUsersDTO();
        anonymousUsersDTO1.setId(1L);
        AnonymousUsersDTO anonymousUsersDTO2 = new AnonymousUsersDTO();
        assertThat(anonymousUsersDTO1).isNotEqualTo(anonymousUsersDTO2);
        anonymousUsersDTO2.setId(anonymousUsersDTO1.getId());
        assertThat(anonymousUsersDTO1).isEqualTo(anonymousUsersDTO2);
        anonymousUsersDTO2.setId(2L);
        assertThat(anonymousUsersDTO1).isNotEqualTo(anonymousUsersDTO2);
        anonymousUsersDTO1.setId(null);
        assertThat(anonymousUsersDTO1).isNotEqualTo(anonymousUsersDTO2);
    }
}
