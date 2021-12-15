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

public class TranslationOverridesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslationOverridesDTO.class);
        TranslationOverridesDTO translationOverridesDTO1 = new TranslationOverridesDTO();
        translationOverridesDTO1.setId(1L);
        TranslationOverridesDTO translationOverridesDTO2 = new TranslationOverridesDTO();
        assertThat(translationOverridesDTO1).isNotEqualTo(translationOverridesDTO2);
        translationOverridesDTO2.setId(translationOverridesDTO1.getId());
        assertThat(translationOverridesDTO1).isEqualTo(translationOverridesDTO2);
        translationOverridesDTO2.setId(2L);
        assertThat(translationOverridesDTO1).isNotEqualTo(translationOverridesDTO2);
        translationOverridesDTO1.setId(null);
        assertThat(translationOverridesDTO1).isNotEqualTo(translationOverridesDTO2);
    }
}
