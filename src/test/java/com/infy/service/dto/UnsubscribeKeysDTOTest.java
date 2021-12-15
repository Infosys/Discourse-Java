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

public class UnsubscribeKeysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnsubscribeKeysDTO.class);
        UnsubscribeKeysDTO unsubscribeKeysDTO1 = new UnsubscribeKeysDTO();
        unsubscribeKeysDTO1.setId(1L);
        UnsubscribeKeysDTO unsubscribeKeysDTO2 = new UnsubscribeKeysDTO();
        assertThat(unsubscribeKeysDTO1).isNotEqualTo(unsubscribeKeysDTO2);
        unsubscribeKeysDTO2.setId(unsubscribeKeysDTO1.getId());
        assertThat(unsubscribeKeysDTO1).isEqualTo(unsubscribeKeysDTO2);
        unsubscribeKeysDTO2.setId(2L);
        assertThat(unsubscribeKeysDTO1).isNotEqualTo(unsubscribeKeysDTO2);
        unsubscribeKeysDTO1.setId(null);
        assertThat(unsubscribeKeysDTO1).isNotEqualTo(unsubscribeKeysDTO2);
    }
}
