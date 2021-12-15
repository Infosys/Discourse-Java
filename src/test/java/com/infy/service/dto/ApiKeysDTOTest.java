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

public class ApiKeysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiKeysDTO.class);
        ApiKeysDTO apiKeysDTO1 = new ApiKeysDTO();
        apiKeysDTO1.setId(1L);
        ApiKeysDTO apiKeysDTO2 = new ApiKeysDTO();
        assertThat(apiKeysDTO1).isNotEqualTo(apiKeysDTO2);
        apiKeysDTO2.setId(apiKeysDTO1.getId());
        assertThat(apiKeysDTO1).isEqualTo(apiKeysDTO2);
        apiKeysDTO2.setId(2L);
        assertThat(apiKeysDTO1).isNotEqualTo(apiKeysDTO2);
        apiKeysDTO1.setId(null);
        assertThat(apiKeysDTO1).isNotEqualTo(apiKeysDTO2);
    }
}
