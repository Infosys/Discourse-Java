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

public class TopicInvitesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicInvitesDTO.class);
        TopicInvitesDTO topicInvitesDTO1 = new TopicInvitesDTO();
        topicInvitesDTO1.setId(1L);
        TopicInvitesDTO topicInvitesDTO2 = new TopicInvitesDTO();
        assertThat(topicInvitesDTO1).isNotEqualTo(topicInvitesDTO2);
        topicInvitesDTO2.setId(topicInvitesDTO1.getId());
        assertThat(topicInvitesDTO1).isEqualTo(topicInvitesDTO2);
        topicInvitesDTO2.setId(2L);
        assertThat(topicInvitesDTO1).isNotEqualTo(topicInvitesDTO2);
        topicInvitesDTO1.setId(null);
        assertThat(topicInvitesDTO1).isNotEqualTo(topicInvitesDTO2);
    }
}
