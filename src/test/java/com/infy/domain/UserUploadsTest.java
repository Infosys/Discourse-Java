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

public class UserUploadsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserUploads.class);
        UserUploads userUploads1 = new UserUploads();
        userUploads1.setId(1L);
        UserUploads userUploads2 = new UserUploads();
        userUploads2.setId(userUploads1.getId());
        assertThat(userUploads1).isEqualTo(userUploads2);
        userUploads2.setId(2L);
        assertThat(userUploads1).isNotEqualTo(userUploads2);
        userUploads1.setId(null);
        assertThat(userUploads1).isNotEqualTo(userUploads2);
    }
}
