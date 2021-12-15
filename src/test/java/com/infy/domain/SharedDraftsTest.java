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

public class SharedDraftsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SharedDrafts.class);
        SharedDrafts sharedDrafts1 = new SharedDrafts();
        sharedDrafts1.setId(1L);
        SharedDrafts sharedDrafts2 = new SharedDrafts();
        sharedDrafts2.setId(sharedDrafts1.getId());
        assertThat(sharedDrafts1).isEqualTo(sharedDrafts2);
        sharedDrafts2.setId(2L);
        assertThat(sharedDrafts1).isNotEqualTo(sharedDrafts2);
        sharedDrafts1.setId(null);
        assertThat(sharedDrafts1).isNotEqualTo(sharedDrafts2);
    }
}
