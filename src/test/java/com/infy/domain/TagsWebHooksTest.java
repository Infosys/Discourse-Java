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

public class TagsWebHooksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagsWebHooks.class);
        TagsWebHooks tagsWebHooks1 = new TagsWebHooks();
        tagsWebHooks1.setId(1L);
        TagsWebHooks tagsWebHooks2 = new TagsWebHooks();
        tagsWebHooks2.setId(tagsWebHooks1.getId());
        assertThat(tagsWebHooks1).isEqualTo(tagsWebHooks2);
        tagsWebHooks2.setId(2L);
        assertThat(tagsWebHooks1).isNotEqualTo(tagsWebHooks2);
        tagsWebHooks1.setId(null);
        assertThat(tagsWebHooks1).isNotEqualTo(tagsWebHooks2);
    }
}
