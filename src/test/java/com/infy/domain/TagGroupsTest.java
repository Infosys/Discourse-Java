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

public class TagGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagGroups.class);
        TagGroups tagGroups1 = new TagGroups();
        tagGroups1.setId(1L);
        TagGroups tagGroups2 = new TagGroups();
        tagGroups2.setId(tagGroups1.getId());
        assertThat(tagGroups1).isEqualTo(tagGroups2);
        tagGroups2.setId(2L);
        assertThat(tagGroups1).isNotEqualTo(tagGroups2);
        tagGroups1.setId(null);
        assertThat(tagGroups1).isNotEqualTo(tagGroups2);
    }
}
