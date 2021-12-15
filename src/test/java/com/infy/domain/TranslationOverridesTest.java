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

public class TranslationOverridesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslationOverrides.class);
        TranslationOverrides translationOverrides1 = new TranslationOverrides();
        translationOverrides1.setId(1L);
        TranslationOverrides translationOverrides2 = new TranslationOverrides();
        translationOverrides2.setId(translationOverrides1.getId());
        assertThat(translationOverrides1).isEqualTo(translationOverrides2);
        translationOverrides2.setId(2L);
        assertThat(translationOverrides1).isNotEqualTo(translationOverrides2);
        translationOverrides1.setId(null);
        assertThat(translationOverrides1).isNotEqualTo(translationOverrides2);
    }
}
