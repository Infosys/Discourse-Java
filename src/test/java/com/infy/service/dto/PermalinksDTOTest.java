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

public class PermalinksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermalinksDTO.class);
        PermalinksDTO permalinksDTO1 = new PermalinksDTO();
        permalinksDTO1.setId(1L);
        PermalinksDTO permalinksDTO2 = new PermalinksDTO();
        assertThat(permalinksDTO1).isNotEqualTo(permalinksDTO2);
        permalinksDTO2.setId(permalinksDTO1.getId());
        assertThat(permalinksDTO1).isEqualTo(permalinksDTO2);
        permalinksDTO2.setId(2L);
        assertThat(permalinksDTO1).isNotEqualTo(permalinksDTO2);
        permalinksDTO1.setId(null);
        assertThat(permalinksDTO1).isNotEqualTo(permalinksDTO2);
    }
}
