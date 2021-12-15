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

public class Oauth2UserInfosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oauth2UserInfos.class);
        Oauth2UserInfos oauth2UserInfos1 = new Oauth2UserInfos();
        oauth2UserInfos1.setId(1L);
        Oauth2UserInfos oauth2UserInfos2 = new Oauth2UserInfos();
        oauth2UserInfos2.setId(oauth2UserInfos1.getId());
        assertThat(oauth2UserInfos1).isEqualTo(oauth2UserInfos2);
        oauth2UserInfos2.setId(2L);
        assertThat(oauth2UserInfos1).isNotEqualTo(oauth2UserInfos2);
        oauth2UserInfos1.setId(null);
        assertThat(oauth2UserInfos1).isNotEqualTo(oauth2UserInfos2);
    }
}
