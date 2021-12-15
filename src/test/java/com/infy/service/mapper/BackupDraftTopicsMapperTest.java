/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BackupDraftTopicsMapperTest {

    private BackupDraftTopicsMapper backupDraftTopicsMapper;

    @BeforeEach
    public void setUp() {
        backupDraftTopicsMapper = new BackupDraftTopicsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(backupDraftTopicsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(backupDraftTopicsMapper.fromId(null)).isNull();
    }
}
