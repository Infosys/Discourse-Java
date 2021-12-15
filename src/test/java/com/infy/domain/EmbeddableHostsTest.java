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

public class EmbeddableHostsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmbeddableHosts.class);
        EmbeddableHosts embeddableHosts1 = new EmbeddableHosts();
        embeddableHosts1.setId(1L);
        EmbeddableHosts embeddableHosts2 = new EmbeddableHosts();
        embeddableHosts2.setId(embeddableHosts1.getId());
        assertThat(embeddableHosts1).isEqualTo(embeddableHosts2);
        embeddableHosts2.setId(2L);
        assertThat(embeddableHosts1).isNotEqualTo(embeddableHosts2);
        embeddableHosts1.setId(null);
        assertThat(embeddableHosts1).isNotEqualTo(embeddableHosts2);
    }
}
