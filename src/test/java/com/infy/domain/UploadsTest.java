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

public class UploadsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uploads.class);
        Uploads uploads1 = new Uploads();
        uploads1.setId(1L);
        Uploads uploads2 = new Uploads();
        uploads2.setId(uploads1.getId());
        assertThat(uploads1).isEqualTo(uploads2);
        uploads2.setId(2L);
        assertThat(uploads1).isNotEqualTo(uploads2);
        uploads1.setId(null);
        assertThat(uploads1).isNotEqualTo(uploads2);
    }
}
