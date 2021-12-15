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

public class TagGroupPermissionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagGroupPermissions.class);
        TagGroupPermissions tagGroupPermissions1 = new TagGroupPermissions();
        tagGroupPermissions1.setId(1L);
        TagGroupPermissions tagGroupPermissions2 = new TagGroupPermissions();
        tagGroupPermissions2.setId(tagGroupPermissions1.getId());
        assertThat(tagGroupPermissions1).isEqualTo(tagGroupPermissions2);
        tagGroupPermissions2.setId(2L);
        assertThat(tagGroupPermissions1).isNotEqualTo(tagGroupPermissions2);
        tagGroupPermissions1.setId(null);
        assertThat(tagGroupPermissions1).isNotEqualTo(tagGroupPermissions2);
    }
}
