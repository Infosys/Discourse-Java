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

public class PluginStoreRowsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PluginStoreRowsDTO.class);
        PluginStoreRowsDTO pluginStoreRowsDTO1 = new PluginStoreRowsDTO();
        pluginStoreRowsDTO1.setId(1L);
        PluginStoreRowsDTO pluginStoreRowsDTO2 = new PluginStoreRowsDTO();
        assertThat(pluginStoreRowsDTO1).isNotEqualTo(pluginStoreRowsDTO2);
        pluginStoreRowsDTO2.setId(pluginStoreRowsDTO1.getId());
        assertThat(pluginStoreRowsDTO1).isEqualTo(pluginStoreRowsDTO2);
        pluginStoreRowsDTO2.setId(2L);
        assertThat(pluginStoreRowsDTO1).isNotEqualTo(pluginStoreRowsDTO2);
        pluginStoreRowsDTO1.setId(null);
        assertThat(pluginStoreRowsDTO1).isNotEqualTo(pluginStoreRowsDTO2);
    }
}
