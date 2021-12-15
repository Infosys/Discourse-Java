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

public class GroupMentionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMentions.class);
        GroupMentions groupMentions1 = new GroupMentions();
        groupMentions1.setId(1L);
        GroupMentions groupMentions2 = new GroupMentions();
        groupMentions2.setId(groupMentions1.getId());
        assertThat(groupMentions1).isEqualTo(groupMentions2);
        groupMentions2.setId(2L);
        assertThat(groupMentions1).isNotEqualTo(groupMentions2);
        groupMentions1.setId(null);
        assertThat(groupMentions1).isNotEqualTo(groupMentions2);
    }
}
