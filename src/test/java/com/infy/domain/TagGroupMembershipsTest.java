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

public class TagGroupMembershipsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagGroupMemberships.class);
        TagGroupMemberships tagGroupMemberships1 = new TagGroupMemberships();
        tagGroupMemberships1.setId(1L);
        TagGroupMemberships tagGroupMemberships2 = new TagGroupMemberships();
        tagGroupMemberships2.setId(tagGroupMemberships1.getId());
        assertThat(tagGroupMemberships1).isEqualTo(tagGroupMemberships2);
        tagGroupMemberships2.setId(2L);
        assertThat(tagGroupMemberships1).isNotEqualTo(tagGroupMemberships2);
        tagGroupMemberships1.setId(null);
        assertThat(tagGroupMemberships1).isNotEqualTo(tagGroupMemberships2);
    }
}
