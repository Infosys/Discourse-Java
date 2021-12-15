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

public class TagGroupMembershipsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagGroupMembershipsDTO.class);
        TagGroupMembershipsDTO tagGroupMembershipsDTO1 = new TagGroupMembershipsDTO();
        tagGroupMembershipsDTO1.setId(1L);
        TagGroupMembershipsDTO tagGroupMembershipsDTO2 = new TagGroupMembershipsDTO();
        assertThat(tagGroupMembershipsDTO1).isNotEqualTo(tagGroupMembershipsDTO2);
        tagGroupMembershipsDTO2.setId(tagGroupMembershipsDTO1.getId());
        assertThat(tagGroupMembershipsDTO1).isEqualTo(tagGroupMembershipsDTO2);
        tagGroupMembershipsDTO2.setId(2L);
        assertThat(tagGroupMembershipsDTO1).isNotEqualTo(tagGroupMembershipsDTO2);
        tagGroupMembershipsDTO1.setId(null);
        assertThat(tagGroupMembershipsDTO1).isNotEqualTo(tagGroupMembershipsDTO2);
    }
}
