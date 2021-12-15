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

public class ArInternalMetadataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArInternalMetadata.class);
        ArInternalMetadata arInternalMetadata1 = new ArInternalMetadata();
        arInternalMetadata1.setId(1L);
        ArInternalMetadata arInternalMetadata2 = new ArInternalMetadata();
        arInternalMetadata2.setId(arInternalMetadata1.getId());
        assertThat(arInternalMetadata1).isEqualTo(arInternalMetadata2);
        arInternalMetadata2.setId(2L);
        assertThat(arInternalMetadata1).isNotEqualTo(arInternalMetadata2);
        arInternalMetadata1.setId(null);
        assertThat(arInternalMetadata1).isNotEqualTo(arInternalMetadata2);
    }
}
