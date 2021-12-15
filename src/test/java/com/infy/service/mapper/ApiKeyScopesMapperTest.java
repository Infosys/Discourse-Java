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

public class ApiKeyScopesMapperTest {

    private ApiKeyScopesMapper apiKeyScopesMapper;

    @BeforeEach
    public void setUp() {
        apiKeyScopesMapper = new ApiKeyScopesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(apiKeyScopesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(apiKeyScopesMapper.fromId(null)).isNull();
    }
}
