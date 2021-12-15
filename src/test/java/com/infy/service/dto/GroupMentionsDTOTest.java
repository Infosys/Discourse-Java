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

public class GroupMentionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMentionsDTO.class);
        GroupMentionsDTO groupMentionsDTO1 = new GroupMentionsDTO();
        groupMentionsDTO1.setId(1L);
        GroupMentionsDTO groupMentionsDTO2 = new GroupMentionsDTO();
        assertThat(groupMentionsDTO1).isNotEqualTo(groupMentionsDTO2);
        groupMentionsDTO2.setId(groupMentionsDTO1.getId());
        assertThat(groupMentionsDTO1).isEqualTo(groupMentionsDTO2);
        groupMentionsDTO2.setId(2L);
        assertThat(groupMentionsDTO1).isNotEqualTo(groupMentionsDTO2);
        groupMentionsDTO1.setId(null);
        assertThat(groupMentionsDTO1).isNotEqualTo(groupMentionsDTO2);
    }
}
