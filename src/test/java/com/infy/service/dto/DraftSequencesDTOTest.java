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

public class DraftSequencesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DraftSequencesDTO.class);
        DraftSequencesDTO draftSequencesDTO1 = new DraftSequencesDTO();
        draftSequencesDTO1.setId(1L);
        DraftSequencesDTO draftSequencesDTO2 = new DraftSequencesDTO();
        assertThat(draftSequencesDTO1).isNotEqualTo(draftSequencesDTO2);
        draftSequencesDTO2.setId(draftSequencesDTO1.getId());
        assertThat(draftSequencesDTO1).isEqualTo(draftSequencesDTO2);
        draftSequencesDTO2.setId(2L);
        assertThat(draftSequencesDTO1).isNotEqualTo(draftSequencesDTO2);
        draftSequencesDTO1.setId(null);
        assertThat(draftSequencesDTO1).isNotEqualTo(draftSequencesDTO2);
    }
}
