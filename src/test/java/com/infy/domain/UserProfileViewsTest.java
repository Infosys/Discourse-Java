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

public class UserProfileViewsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfileViews.class);
        UserProfileViews userProfileViews1 = new UserProfileViews();
        userProfileViews1.setId(1L);
        UserProfileViews userProfileViews2 = new UserProfileViews();
        userProfileViews2.setId(userProfileViews1.getId());
        assertThat(userProfileViews1).isEqualTo(userProfileViews2);
        userProfileViews2.setId(2L);
        assertThat(userProfileViews1).isNotEqualTo(userProfileViews2);
        userProfileViews1.setId(null);
        assertThat(userProfileViews1).isNotEqualTo(userProfileViews2);
    }
}
