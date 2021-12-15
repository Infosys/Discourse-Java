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

public class SchemaMigrationsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaMigrationsDTO.class);
        SchemaMigrationsDTO schemaMigrationsDTO1 = new SchemaMigrationsDTO();
        schemaMigrationsDTO1.setId(1L);
        SchemaMigrationsDTO schemaMigrationsDTO2 = new SchemaMigrationsDTO();
        assertThat(schemaMigrationsDTO1).isNotEqualTo(schemaMigrationsDTO2);
        schemaMigrationsDTO2.setId(schemaMigrationsDTO1.getId());
        assertThat(schemaMigrationsDTO1).isEqualTo(schemaMigrationsDTO2);
        schemaMigrationsDTO2.setId(2L);
        assertThat(schemaMigrationsDTO1).isNotEqualTo(schemaMigrationsDTO2);
        schemaMigrationsDTO1.setId(null);
        assertThat(schemaMigrationsDTO1).isNotEqualTo(schemaMigrationsDTO2);
    }
}
