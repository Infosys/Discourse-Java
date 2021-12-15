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

public class CustomEmojisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomEmojis.class);
        CustomEmojis customEmojis1 = new CustomEmojis();
        customEmojis1.setId(1L);
        CustomEmojis customEmojis2 = new CustomEmojis();
        customEmojis2.setId(customEmojis1.getId());
        assertThat(customEmojis1).isEqualTo(customEmojis2);
        customEmojis2.setId(2L);
        assertThat(customEmojis1).isNotEqualTo(customEmojis2);
        customEmojis1.setId(null);
        assertThat(customEmojis1).isNotEqualTo(customEmojis2);
    }
}
