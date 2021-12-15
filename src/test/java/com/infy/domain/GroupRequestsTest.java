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

public class GroupRequestsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupRequests.class);
        GroupRequests groupRequests1 = new GroupRequests();
        groupRequests1.setId(1L);
        GroupRequests groupRequests2 = new GroupRequests();
        groupRequests2.setId(groupRequests1.getId());
        assertThat(groupRequests1).isEqualTo(groupRequests2);
        groupRequests2.setId(2L);
        assertThat(groupRequests1).isNotEqualTo(groupRequests2);
        groupRequests1.setId(null);
        assertThat(groupRequests1).isNotEqualTo(groupRequests2);
    }
}
