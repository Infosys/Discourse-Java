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

public class PublishedPagesMapperTest {

    private PublishedPagesMapper publishedPagesMapper;

    @BeforeEach
    public void setUp() {
        publishedPagesMapper = new PublishedPagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(publishedPagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(publishedPagesMapper.fromId(null)).isNull();
    }
}
