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

public class TagUsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagUsers.class);
        TagUsers tagUsers1 = new TagUsers();
        tagUsers1.setId(1L);
        TagUsers tagUsers2 = new TagUsers();
        tagUsers2.setId(tagUsers1.getId());
        assertThat(tagUsers1).isEqualTo(tagUsers2);
        tagUsers2.setId(2L);
        assertThat(tagUsers1).isNotEqualTo(tagUsers2);
        tagUsers1.setId(null);
        assertThat(tagUsers1).isNotEqualTo(tagUsers2);
    }
}
