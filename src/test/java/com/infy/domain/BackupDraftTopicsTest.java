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

public class BackupDraftTopicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackupDraftTopics.class);
        BackupDraftTopics backupDraftTopics1 = new BackupDraftTopics();
        backupDraftTopics1.setId(1L);
        BackupDraftTopics backupDraftTopics2 = new BackupDraftTopics();
        backupDraftTopics2.setId(backupDraftTopics1.getId());
        assertThat(backupDraftTopics1).isEqualTo(backupDraftTopics2);
        backupDraftTopics2.setId(2L);
        assertThat(backupDraftTopics1).isNotEqualTo(backupDraftTopics2);
        backupDraftTopics1.setId(null);
        assertThat(backupDraftTopics1).isNotEqualTo(backupDraftTopics2);
    }
}
