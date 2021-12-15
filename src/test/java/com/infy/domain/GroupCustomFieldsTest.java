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

public class GroupCustomFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCustomFields.class);
        GroupCustomFields groupCustomFields1 = new GroupCustomFields();
        groupCustomFields1.setId(1L);
        GroupCustomFields groupCustomFields2 = new GroupCustomFields();
        groupCustomFields2.setId(groupCustomFields1.getId());
        assertThat(groupCustomFields1).isEqualTo(groupCustomFields2);
        groupCustomFields2.setId(2L);
        assertThat(groupCustomFields1).isNotEqualTo(groupCustomFields2);
        groupCustomFields1.setId(null);
        assertThat(groupCustomFields1).isNotEqualTo(groupCustomFields2);
    }
}
