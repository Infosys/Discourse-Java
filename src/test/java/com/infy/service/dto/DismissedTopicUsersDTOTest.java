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

public class DismissedTopicUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DismissedTopicUsersDTO.class);
        DismissedTopicUsersDTO dismissedTopicUsersDTO1 = new DismissedTopicUsersDTO();
        dismissedTopicUsersDTO1.setId(1L);
        DismissedTopicUsersDTO dismissedTopicUsersDTO2 = new DismissedTopicUsersDTO();
        assertThat(dismissedTopicUsersDTO1).isNotEqualTo(dismissedTopicUsersDTO2);
        dismissedTopicUsersDTO2.setId(dismissedTopicUsersDTO1.getId());
        assertThat(dismissedTopicUsersDTO1).isEqualTo(dismissedTopicUsersDTO2);
        dismissedTopicUsersDTO2.setId(2L);
        assertThat(dismissedTopicUsersDTO1).isNotEqualTo(dismissedTopicUsersDTO2);
        dismissedTopicUsersDTO1.setId(null);
        assertThat(dismissedTopicUsersDTO1).isNotEqualTo(dismissedTopicUsersDTO2);
    }
}
