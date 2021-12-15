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

public class BadgesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Badges.class);
        Badges badges1 = new Badges();
        badges1.setId(1L);
        Badges badges2 = new Badges();
        badges2.setId(badges1.getId());
        assertThat(badges1).isEqualTo(badges2);
        badges2.setId(2L);
        assertThat(badges1).isNotEqualTo(badges2);
        badges1.setId(null);
        assertThat(badges1).isNotEqualTo(badges2);
    }
}
