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

public class TagSearchDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagSearchDataDTO.class);
        TagSearchDataDTO tagSearchDataDTO1 = new TagSearchDataDTO();
        tagSearchDataDTO1.setId(1L);
        TagSearchDataDTO tagSearchDataDTO2 = new TagSearchDataDTO();
        assertThat(tagSearchDataDTO1).isNotEqualTo(tagSearchDataDTO2);
        tagSearchDataDTO2.setId(tagSearchDataDTO1.getId());
        assertThat(tagSearchDataDTO1).isEqualTo(tagSearchDataDTO2);
        tagSearchDataDTO2.setId(2L);
        assertThat(tagSearchDataDTO1).isNotEqualTo(tagSearchDataDTO2);
        tagSearchDataDTO1.setId(null);
        assertThat(tagSearchDataDTO1).isNotEqualTo(tagSearchDataDTO2);
    }
}
