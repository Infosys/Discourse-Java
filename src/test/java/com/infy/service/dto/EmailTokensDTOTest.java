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

public class EmailTokensDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTokensDTO.class);
        EmailTokensDTO emailTokensDTO1 = new EmailTokensDTO();
        emailTokensDTO1.setId(1L);
        EmailTokensDTO emailTokensDTO2 = new EmailTokensDTO();
        assertThat(emailTokensDTO1).isNotEqualTo(emailTokensDTO2);
        emailTokensDTO2.setId(emailTokensDTO1.getId());
        assertThat(emailTokensDTO1).isEqualTo(emailTokensDTO2);
        emailTokensDTO2.setId(2L);
        assertThat(emailTokensDTO1).isNotEqualTo(emailTokensDTO2);
        emailTokensDTO1.setId(null);
        assertThat(emailTokensDTO1).isNotEqualTo(emailTokensDTO2);
    }
}
