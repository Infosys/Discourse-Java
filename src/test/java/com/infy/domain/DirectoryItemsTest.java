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

public class DirectoryItemsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectoryItems.class);
        DirectoryItems directoryItems1 = new DirectoryItems();
        directoryItems1.setId(1L);
        DirectoryItems directoryItems2 = new DirectoryItems();
        directoryItems2.setId(directoryItems1.getId());
        assertThat(directoryItems1).isEqualTo(directoryItems2);
        directoryItems2.setId(2L);
        assertThat(directoryItems1).isNotEqualTo(directoryItems2);
        directoryItems1.setId(null);
        assertThat(directoryItems1).isNotEqualTo(directoryItems2);
    }
}
