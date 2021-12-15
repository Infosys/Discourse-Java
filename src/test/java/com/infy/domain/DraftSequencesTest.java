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

public class DraftSequencesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DraftSequences.class);
        DraftSequences draftSequences1 = new DraftSequences();
        draftSequences1.setId(1L);
        DraftSequences draftSequences2 = new DraftSequences();
        draftSequences2.setId(draftSequences1.getId());
        assertThat(draftSequences1).isEqualTo(draftSequences2);
        draftSequences2.setId(2L);
        assertThat(draftSequences1).isNotEqualTo(draftSequences2);
        draftSequences1.setId(null);
        assertThat(draftSequences1).isNotEqualTo(draftSequences2);
    }
}
