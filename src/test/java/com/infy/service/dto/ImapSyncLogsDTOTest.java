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

public class ImapSyncLogsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImapSyncLogsDTO.class);
        ImapSyncLogsDTO imapSyncLogsDTO1 = new ImapSyncLogsDTO();
        imapSyncLogsDTO1.setId(1L);
        ImapSyncLogsDTO imapSyncLogsDTO2 = new ImapSyncLogsDTO();
        assertThat(imapSyncLogsDTO1).isNotEqualTo(imapSyncLogsDTO2);
        imapSyncLogsDTO2.setId(imapSyncLogsDTO1.getId());
        assertThat(imapSyncLogsDTO1).isEqualTo(imapSyncLogsDTO2);
        imapSyncLogsDTO2.setId(2L);
        assertThat(imapSyncLogsDTO1).isNotEqualTo(imapSyncLogsDTO2);
        imapSyncLogsDTO1.setId(null);
        assertThat(imapSyncLogsDTO1).isNotEqualTo(imapSyncLogsDTO2);
    }
}
