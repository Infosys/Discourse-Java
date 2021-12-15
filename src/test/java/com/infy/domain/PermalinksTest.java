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

public class PermalinksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Permalinks.class);
        Permalinks permalinks1 = new Permalinks();
        permalinks1.setId(1L);
        Permalinks permalinks2 = new Permalinks();
        permalinks2.setId(permalinks1.getId());
        assertThat(permalinks1).isEqualTo(permalinks2);
        permalinks2.setId(2L);
        assertThat(permalinks1).isNotEqualTo(permalinks2);
        permalinks1.setId(null);
        assertThat(permalinks1).isNotEqualTo(permalinks2);
    }
}
