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

public class PluginStoreRowsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PluginStoreRows.class);
        PluginStoreRows pluginStoreRows1 = new PluginStoreRows();
        pluginStoreRows1.setId(1L);
        PluginStoreRows pluginStoreRows2 = new PluginStoreRows();
        pluginStoreRows2.setId(pluginStoreRows1.getId());
        assertThat(pluginStoreRows1).isEqualTo(pluginStoreRows2);
        pluginStoreRows2.setId(2L);
        assertThat(pluginStoreRows1).isNotEqualTo(pluginStoreRows2);
        pluginStoreRows1.setId(null);
        assertThat(pluginStoreRows1).isNotEqualTo(pluginStoreRows2);
    }
}
