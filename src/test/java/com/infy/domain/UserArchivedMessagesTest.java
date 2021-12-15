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

public class UserArchivedMessagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserArchivedMessages.class);
        UserArchivedMessages userArchivedMessages1 = new UserArchivedMessages();
        userArchivedMessages1.setId(1L);
        UserArchivedMessages userArchivedMessages2 = new UserArchivedMessages();
        userArchivedMessages2.setId(userArchivedMessages1.getId());
        assertThat(userArchivedMessages1).isEqualTo(userArchivedMessages2);
        userArchivedMessages2.setId(2L);
        assertThat(userArchivedMessages1).isNotEqualTo(userArchivedMessages2);
        userArchivedMessages1.setId(null);
        assertThat(userArchivedMessages1).isNotEqualTo(userArchivedMessages2);
    }
}
