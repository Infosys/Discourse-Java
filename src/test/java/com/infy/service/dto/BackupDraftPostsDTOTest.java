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

public class BackupDraftPostsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackupDraftPostsDTO.class);
        BackupDraftPostsDTO backupDraftPostsDTO1 = new BackupDraftPostsDTO();
        backupDraftPostsDTO1.setId(1L);
        BackupDraftPostsDTO backupDraftPostsDTO2 = new BackupDraftPostsDTO();
        assertThat(backupDraftPostsDTO1).isNotEqualTo(backupDraftPostsDTO2);
        backupDraftPostsDTO2.setId(backupDraftPostsDTO1.getId());
        assertThat(backupDraftPostsDTO1).isEqualTo(backupDraftPostsDTO2);
        backupDraftPostsDTO2.setId(2L);
        assertThat(backupDraftPostsDTO1).isNotEqualTo(backupDraftPostsDTO2);
        backupDraftPostsDTO1.setId(null);
        assertThat(backupDraftPostsDTO1).isNotEqualTo(backupDraftPostsDTO2);
    }
}
