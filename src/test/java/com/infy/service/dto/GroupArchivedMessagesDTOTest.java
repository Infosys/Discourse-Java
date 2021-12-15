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

public class GroupArchivedMessagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupArchivedMessagesDTO.class);
        GroupArchivedMessagesDTO groupArchivedMessagesDTO1 = new GroupArchivedMessagesDTO();
        groupArchivedMessagesDTO1.setId(1L);
        GroupArchivedMessagesDTO groupArchivedMessagesDTO2 = new GroupArchivedMessagesDTO();
        assertThat(groupArchivedMessagesDTO1).isNotEqualTo(groupArchivedMessagesDTO2);
        groupArchivedMessagesDTO2.setId(groupArchivedMessagesDTO1.getId());
        assertThat(groupArchivedMessagesDTO1).isEqualTo(groupArchivedMessagesDTO2);
        groupArchivedMessagesDTO2.setId(2L);
        assertThat(groupArchivedMessagesDTO1).isNotEqualTo(groupArchivedMessagesDTO2);
        groupArchivedMessagesDTO1.setId(null);
        assertThat(groupArchivedMessagesDTO1).isNotEqualTo(groupArchivedMessagesDTO2);
    }
}
