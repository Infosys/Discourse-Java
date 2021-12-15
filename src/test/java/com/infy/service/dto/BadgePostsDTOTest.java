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

public class BadgePostsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgePostsDTO.class);
        BadgePostsDTO badgePostsDTO1 = new BadgePostsDTO();
        badgePostsDTO1.setId(1L);
        BadgePostsDTO badgePostsDTO2 = new BadgePostsDTO();
        assertThat(badgePostsDTO1).isNotEqualTo(badgePostsDTO2);
        badgePostsDTO2.setId(badgePostsDTO1.getId());
        assertThat(badgePostsDTO1).isEqualTo(badgePostsDTO2);
        badgePostsDTO2.setId(2L);
        assertThat(badgePostsDTO1).isNotEqualTo(badgePostsDTO2);
        badgePostsDTO1.setId(null);
        assertThat(badgePostsDTO1).isNotEqualTo(badgePostsDTO2);
    }
}
