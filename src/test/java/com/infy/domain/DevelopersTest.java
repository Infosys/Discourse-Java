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

public class DevelopersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Developers.class);
        Developers developers1 = new Developers();
        developers1.setId(1L);
        Developers developers2 = new Developers();
        developers2.setId(developers1.getId());
        assertThat(developers1).isEqualTo(developers2);
        developers2.setId(2L);
        assertThat(developers1).isNotEqualTo(developers2);
        developers1.setId(null);
        assertThat(developers1).isNotEqualTo(developers2);
    }
}
