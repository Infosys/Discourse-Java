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

public class UserSearchDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSearchData.class);
        UserSearchData userSearchData1 = new UserSearchData();
        userSearchData1.setId(1L);
        UserSearchData userSearchData2 = new UserSearchData();
        userSearchData2.setId(userSearchData1.getId());
        assertThat(userSearchData1).isEqualTo(userSearchData2);
        userSearchData2.setId(2L);
        assertThat(userSearchData1).isNotEqualTo(userSearchData2);
        userSearchData1.setId(null);
        assertThat(userSearchData1).isNotEqualTo(userSearchData2);
    }
}
