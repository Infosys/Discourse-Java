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

public class SharedDraftsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SharedDraftsDTO.class);
        SharedDraftsDTO sharedDraftsDTO1 = new SharedDraftsDTO();
        sharedDraftsDTO1.setId(1L);
        SharedDraftsDTO sharedDraftsDTO2 = new SharedDraftsDTO();
        assertThat(sharedDraftsDTO1).isNotEqualTo(sharedDraftsDTO2);
        sharedDraftsDTO2.setId(sharedDraftsDTO1.getId());
        assertThat(sharedDraftsDTO1).isEqualTo(sharedDraftsDTO2);
        sharedDraftsDTO2.setId(2L);
        assertThat(sharedDraftsDTO1).isNotEqualTo(sharedDraftsDTO2);
        sharedDraftsDTO1.setId(null);
        assertThat(sharedDraftsDTO1).isNotEqualTo(sharedDraftsDTO2);
    }
}
