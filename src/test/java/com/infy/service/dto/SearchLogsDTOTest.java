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

public class SearchLogsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchLogsDTO.class);
        SearchLogsDTO searchLogsDTO1 = new SearchLogsDTO();
        searchLogsDTO1.setId(1L);
        SearchLogsDTO searchLogsDTO2 = new SearchLogsDTO();
        assertThat(searchLogsDTO1).isNotEqualTo(searchLogsDTO2);
        searchLogsDTO2.setId(searchLogsDTO1.getId());
        assertThat(searchLogsDTO1).isEqualTo(searchLogsDTO2);
        searchLogsDTO2.setId(2L);
        assertThat(searchLogsDTO1).isNotEqualTo(searchLogsDTO2);
        searchLogsDTO1.setId(null);
        assertThat(searchLogsDTO1).isNotEqualTo(searchLogsDTO2);
    }
}
