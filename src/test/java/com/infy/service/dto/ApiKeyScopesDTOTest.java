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

public class ApiKeyScopesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiKeyScopesDTO.class);
        ApiKeyScopesDTO apiKeyScopesDTO1 = new ApiKeyScopesDTO();
        apiKeyScopesDTO1.setId(1L);
        ApiKeyScopesDTO apiKeyScopesDTO2 = new ApiKeyScopesDTO();
        assertThat(apiKeyScopesDTO1).isNotEqualTo(apiKeyScopesDTO2);
        apiKeyScopesDTO2.setId(apiKeyScopesDTO1.getId());
        assertThat(apiKeyScopesDTO1).isEqualTo(apiKeyScopesDTO2);
        apiKeyScopesDTO2.setId(2L);
        assertThat(apiKeyScopesDTO1).isNotEqualTo(apiKeyScopesDTO2);
        apiKeyScopesDTO1.setId(null);
        assertThat(apiKeyScopesDTO1).isNotEqualTo(apiKeyScopesDTO2);
    }
}
