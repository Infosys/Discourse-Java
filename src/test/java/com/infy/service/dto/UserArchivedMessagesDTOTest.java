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

public class UserArchivedMessagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserArchivedMessagesDTO.class);
        UserArchivedMessagesDTO userArchivedMessagesDTO1 = new UserArchivedMessagesDTO();
        userArchivedMessagesDTO1.setId(1L);
        UserArchivedMessagesDTO userArchivedMessagesDTO2 = new UserArchivedMessagesDTO();
        assertThat(userArchivedMessagesDTO1).isNotEqualTo(userArchivedMessagesDTO2);
        userArchivedMessagesDTO2.setId(userArchivedMessagesDTO1.getId());
        assertThat(userArchivedMessagesDTO1).isEqualTo(userArchivedMessagesDTO2);
        userArchivedMessagesDTO2.setId(2L);
        assertThat(userArchivedMessagesDTO1).isNotEqualTo(userArchivedMessagesDTO2);
        userArchivedMessagesDTO1.setId(null);
        assertThat(userArchivedMessagesDTO1).isNotEqualTo(userArchivedMessagesDTO2);
    }
}
