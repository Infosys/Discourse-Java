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

public class ThemeModifierSetsMapperTest {

    private ThemeModifierSetsMapper themeModifierSetsMapper;

    @BeforeEach
    public void setUp() {
        themeModifierSetsMapper = new ThemeModifierSetsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(themeModifierSetsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(themeModifierSetsMapper.fromId(null)).isNull();
    }
}
