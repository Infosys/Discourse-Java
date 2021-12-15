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

public class BackupDraftPostsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackupDraftPosts.class);
        BackupDraftPosts backupDraftPosts1 = new BackupDraftPosts();
        backupDraftPosts1.setId(1L);
        BackupDraftPosts backupDraftPosts2 = new BackupDraftPosts();
        backupDraftPosts2.setId(backupDraftPosts1.getId());
        assertThat(backupDraftPosts1).isEqualTo(backupDraftPosts2);
        backupDraftPosts2.setId(2L);
        assertThat(backupDraftPosts1).isNotEqualTo(backupDraftPosts2);
        backupDraftPosts1.setId(null);
        assertThat(backupDraftPosts1).isNotEqualTo(backupDraftPosts2);
    }
}
