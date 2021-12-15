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

public class ArInternalMetadataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArInternalMetadataDTO.class);
        ArInternalMetadataDTO arInternalMetadataDTO1 = new ArInternalMetadataDTO();
        arInternalMetadataDTO1.setId(1L);
        ArInternalMetadataDTO arInternalMetadataDTO2 = new ArInternalMetadataDTO();
        assertThat(arInternalMetadataDTO1).isNotEqualTo(arInternalMetadataDTO2);
        arInternalMetadataDTO2.setId(arInternalMetadataDTO1.getId());
        assertThat(arInternalMetadataDTO1).isEqualTo(arInternalMetadataDTO2);
        arInternalMetadataDTO2.setId(2L);
        assertThat(arInternalMetadataDTO1).isNotEqualTo(arInternalMetadataDTO2);
        arInternalMetadataDTO1.setId(null);
        assertThat(arInternalMetadataDTO1).isNotEqualTo(arInternalMetadataDTO2);
    }
}
