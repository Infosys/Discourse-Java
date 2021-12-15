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

public class DraftsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DraftsDTO.class);
        DraftsDTO draftsDTO1 = new DraftsDTO();
        draftsDTO1.setId(1L);
        DraftsDTO draftsDTO2 = new DraftsDTO();
        assertThat(draftsDTO1).isNotEqualTo(draftsDTO2);
        draftsDTO2.setId(draftsDTO1.getId());
        assertThat(draftsDTO1).isEqualTo(draftsDTO2);
        draftsDTO2.setId(2L);
        assertThat(draftsDTO1).isNotEqualTo(draftsDTO2);
        draftsDTO1.setId(null);
        assertThat(draftsDTO1).isNotEqualTo(draftsDTO2);
    }
}
