/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class Oauth2UserInfosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oauth2UserInfosDTO.class);
        Oauth2UserInfosDTO oauth2UserInfosDTO1 = new Oauth2UserInfosDTO();
        oauth2UserInfosDTO1.setId(1L);
        Oauth2UserInfosDTO oauth2UserInfosDTO2 = new Oauth2UserInfosDTO();
        assertThat(oauth2UserInfosDTO1).isNotEqualTo(oauth2UserInfosDTO2);
        oauth2UserInfosDTO2.setId(oauth2UserInfosDTO1.getId());
        assertThat(oauth2UserInfosDTO1).isEqualTo(oauth2UserInfosDTO2);
        oauth2UserInfosDTO2.setId(2L);
        assertThat(oauth2UserInfosDTO1).isNotEqualTo(oauth2UserInfosDTO2);
        oauth2UserInfosDTO1.setId(null);
        assertThat(oauth2UserInfosDTO1).isNotEqualTo(oauth2UserInfosDTO2);
    }
}
