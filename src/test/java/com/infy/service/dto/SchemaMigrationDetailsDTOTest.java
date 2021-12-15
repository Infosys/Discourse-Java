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

public class SchemaMigrationDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaMigrationDetailsDTO.class);
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO1 = new SchemaMigrationDetailsDTO();
        schemaMigrationDetailsDTO1.setId(1L);
        SchemaMigrationDetailsDTO schemaMigrationDetailsDTO2 = new SchemaMigrationDetailsDTO();
        assertThat(schemaMigrationDetailsDTO1).isNotEqualTo(schemaMigrationDetailsDTO2);
        schemaMigrationDetailsDTO2.setId(schemaMigrationDetailsDTO1.getId());
        assertThat(schemaMigrationDetailsDTO1).isEqualTo(schemaMigrationDetailsDTO2);
        schemaMigrationDetailsDTO2.setId(2L);
        assertThat(schemaMigrationDetailsDTO1).isNotEqualTo(schemaMigrationDetailsDTO2);
        schemaMigrationDetailsDTO1.setId(null);
        assertThat(schemaMigrationDetailsDTO1).isNotEqualTo(schemaMigrationDetailsDTO2);
    }
}
