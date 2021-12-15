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

public class SchemaMigrationsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaMigrations.class);
        SchemaMigrations schemaMigrations1 = new SchemaMigrations();
        schemaMigrations1.setId(1L);
        SchemaMigrations schemaMigrations2 = new SchemaMigrations();
        schemaMigrations2.setId(schemaMigrations1.getId());
        assertThat(schemaMigrations1).isEqualTo(schemaMigrations2);
        schemaMigrations2.setId(2L);
        assertThat(schemaMigrations1).isNotEqualTo(schemaMigrations2);
        schemaMigrations1.setId(null);
        assertThat(schemaMigrations1).isNotEqualTo(schemaMigrations2);
    }
}
