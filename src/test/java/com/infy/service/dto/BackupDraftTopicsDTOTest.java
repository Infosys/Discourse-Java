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

public class BackupDraftTopicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackupDraftTopicsDTO.class);
        BackupDraftTopicsDTO backupDraftTopicsDTO1 = new BackupDraftTopicsDTO();
        backupDraftTopicsDTO1.setId(1L);
        BackupDraftTopicsDTO backupDraftTopicsDTO2 = new BackupDraftTopicsDTO();
        assertThat(backupDraftTopicsDTO1).isNotEqualTo(backupDraftTopicsDTO2);
        backupDraftTopicsDTO2.setId(backupDraftTopicsDTO1.getId());
        assertThat(backupDraftTopicsDTO1).isEqualTo(backupDraftTopicsDTO2);
        backupDraftTopicsDTO2.setId(2L);
        assertThat(backupDraftTopicsDTO1).isNotEqualTo(backupDraftTopicsDTO2);
        backupDraftTopicsDTO1.setId(null);
        assertThat(backupDraftTopicsDTO1).isNotEqualTo(backupDraftTopicsDTO2);
    }
}
