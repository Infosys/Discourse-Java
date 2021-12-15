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

public class SearchLogsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchLogs.class);
        SearchLogs searchLogs1 = new SearchLogs();
        searchLogs1.setId(1L);
        SearchLogs searchLogs2 = new SearchLogs();
        searchLogs2.setId(searchLogs1.getId());
        assertThat(searchLogs1).isEqualTo(searchLogs2);
        searchLogs2.setId(2L);
        assertThat(searchLogs1).isNotEqualTo(searchLogs2);
        searchLogs1.setId(null);
        assertThat(searchLogs1).isNotEqualTo(searchLogs2);
    }
}
