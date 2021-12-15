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

public class TagUsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagUsersDTO.class);
        TagUsersDTO tagUsersDTO1 = new TagUsersDTO();
        tagUsersDTO1.setId(1L);
        TagUsersDTO tagUsersDTO2 = new TagUsersDTO();
        assertThat(tagUsersDTO1).isNotEqualTo(tagUsersDTO2);
        tagUsersDTO2.setId(tagUsersDTO1.getId());
        assertThat(tagUsersDTO1).isEqualTo(tagUsersDTO2);
        tagUsersDTO2.setId(2L);
        assertThat(tagUsersDTO1).isNotEqualTo(tagUsersDTO2);
        tagUsersDTO1.setId(null);
        assertThat(tagUsersDTO1).isNotEqualTo(tagUsersDTO2);
    }
}
