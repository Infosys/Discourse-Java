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

public class TagsWebHooksMapperTest {

    private TagsWebHooksMapper tagsWebHooksMapper;

    @BeforeEach
    public void setUp() {
        tagsWebHooksMapper = new TagsWebHooksMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tagsWebHooksMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tagsWebHooksMapper.fromId(null)).isNull();
    }
}
