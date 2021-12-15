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

public class UnsubscribeKeysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnsubscribeKeys.class);
        UnsubscribeKeys unsubscribeKeys1 = new UnsubscribeKeys();
        unsubscribeKeys1.setId(1L);
        UnsubscribeKeys unsubscribeKeys2 = new UnsubscribeKeys();
        unsubscribeKeys2.setId(unsubscribeKeys1.getId());
        assertThat(unsubscribeKeys1).isEqualTo(unsubscribeKeys2);
        unsubscribeKeys2.setId(2L);
        assertThat(unsubscribeKeys1).isNotEqualTo(unsubscribeKeys2);
        unsubscribeKeys1.setId(null);
        assertThat(unsubscribeKeys1).isNotEqualTo(unsubscribeKeys2);
    }
}
