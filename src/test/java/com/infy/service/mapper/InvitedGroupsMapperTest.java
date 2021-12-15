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

public class InvitedGroupsMapperTest {

    private InvitedGroupsMapper invitedGroupsMapper;

    @BeforeEach
    public void setUp() {
        invitedGroupsMapper = new InvitedGroupsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(invitedGroupsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(invitedGroupsMapper.fromId(null)).isNull();
    }
}
