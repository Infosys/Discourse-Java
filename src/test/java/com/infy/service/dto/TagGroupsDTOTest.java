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

public class TagGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagGroupsDTO.class);
        TagGroupsDTO tagGroupsDTO1 = new TagGroupsDTO();
        tagGroupsDTO1.setId(1L);
        TagGroupsDTO tagGroupsDTO2 = new TagGroupsDTO();
        assertThat(tagGroupsDTO1).isNotEqualTo(tagGroupsDTO2);
        tagGroupsDTO2.setId(tagGroupsDTO1.getId());
        assertThat(tagGroupsDTO1).isEqualTo(tagGroupsDTO2);
        tagGroupsDTO2.setId(2L);
        assertThat(tagGroupsDTO1).isNotEqualTo(tagGroupsDTO2);
        tagGroupsDTO1.setId(null);
        assertThat(tagGroupsDTO1).isNotEqualTo(tagGroupsDTO2);
    }
}
