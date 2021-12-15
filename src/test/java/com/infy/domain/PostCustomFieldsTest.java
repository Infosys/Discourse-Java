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

public class PostCustomFieldsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCustomFields.class);
        PostCustomFields postCustomFields1 = new PostCustomFields();
        postCustomFields1.setId(1L);
        PostCustomFields postCustomFields2 = new PostCustomFields();
        postCustomFields2.setId(postCustomFields1.getId());
        assertThat(postCustomFields1).isEqualTo(postCustomFields2);
        postCustomFields2.setId(2L);
        assertThat(postCustomFields1).isNotEqualTo(postCustomFields2);
        postCustomFields1.setId(null);
        assertThat(postCustomFields1).isNotEqualTo(postCustomFields2);
    }
}
