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

public class SchemaMigrationDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaMigrationDetails.class);
        SchemaMigrationDetails schemaMigrationDetails1 = new SchemaMigrationDetails();
        schemaMigrationDetails1.setId(1L);
        SchemaMigrationDetails schemaMigrationDetails2 = new SchemaMigrationDetails();
        schemaMigrationDetails2.setId(schemaMigrationDetails1.getId());
        assertThat(schemaMigrationDetails1).isEqualTo(schemaMigrationDetails2);
        schemaMigrationDetails2.setId(2L);
        assertThat(schemaMigrationDetails1).isNotEqualTo(schemaMigrationDetails2);
        schemaMigrationDetails1.setId(null);
        assertThat(schemaMigrationDetails1).isNotEqualTo(schemaMigrationDetails2);
    }
}
