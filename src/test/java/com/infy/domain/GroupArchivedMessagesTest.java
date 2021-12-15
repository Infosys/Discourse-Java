/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class GroupArchivedMessagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupArchivedMessages.class);
        GroupArchivedMessages groupArchivedMessages1 = new GroupArchivedMessages();
        groupArchivedMessages1.setId(1L);
        GroupArchivedMessages groupArchivedMessages2 = new GroupArchivedMessages();
        groupArchivedMessages2.setId(groupArchivedMessages1.getId());
        assertThat(groupArchivedMessages1).isEqualTo(groupArchivedMessages2);
        groupArchivedMessages2.setId(2L);
        assertThat(groupArchivedMessages1).isNotEqualTo(groupArchivedMessages2);
        groupArchivedMessages1.setId(null);
        assertThat(groupArchivedMessages1).isNotEqualTo(groupArchivedMessages2);
    }
}
