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

public class UserAuthTokensMapperTest {

    private UserAuthTokensMapper userAuthTokensMapper;

    @BeforeEach
    public void setUp() {
        userAuthTokensMapper = new UserAuthTokensMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userAuthTokensMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userAuthTokensMapper.fromId(null)).isNull();
    }
}
