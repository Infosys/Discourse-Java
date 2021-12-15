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

public class DirectoryItemsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectoryItemsDTO.class);
        DirectoryItemsDTO directoryItemsDTO1 = new DirectoryItemsDTO();
        directoryItemsDTO1.setId(1L);
        DirectoryItemsDTO directoryItemsDTO2 = new DirectoryItemsDTO();
        assertThat(directoryItemsDTO1).isNotEqualTo(directoryItemsDTO2);
        directoryItemsDTO2.setId(directoryItemsDTO1.getId());
        assertThat(directoryItemsDTO1).isEqualTo(directoryItemsDTO2);
        directoryItemsDTO2.setId(2L);
        assertThat(directoryItemsDTO1).isNotEqualTo(directoryItemsDTO2);
        directoryItemsDTO1.setId(null);
        assertThat(directoryItemsDTO1).isNotEqualTo(directoryItemsDTO2);
    }
}
