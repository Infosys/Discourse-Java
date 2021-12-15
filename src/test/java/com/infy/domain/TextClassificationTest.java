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

public class TextClassificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextClassification.class);
        TextClassification textClassification1 = new TextClassification();
        textClassification1.setId(1L);
        TextClassification textClassification2 = new TextClassification();
        textClassification2.setId(textClassification1.getId());
        assertThat(textClassification1).isEqualTo(textClassification2);
        textClassification2.setId(2L);
        assertThat(textClassification1).isNotEqualTo(textClassification2);
        textClassification1.setId(null);
        assertThat(textClassification1).isNotEqualTo(textClassification2);
    }
}
