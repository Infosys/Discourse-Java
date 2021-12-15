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

public class BackupMetadataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackupMetadata.class);
        BackupMetadata backupMetadata1 = new BackupMetadata();
        backupMetadata1.setId(1L);
        BackupMetadata backupMetadata2 = new BackupMetadata();
        backupMetadata2.setId(backupMetadata1.getId());
        assertThat(backupMetadata1).isEqualTo(backupMetadata2);
        backupMetadata2.setId(2L);
        assertThat(backupMetadata1).isNotEqualTo(backupMetadata2);
        backupMetadata1.setId(null);
        assertThat(backupMetadata1).isNotEqualTo(backupMetadata2);
    }
}
