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

public class PostActionTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostActionTypesDTO.class);
        PostActionTypesDTO postActionTypesDTO1 = new PostActionTypesDTO();
        postActionTypesDTO1.setId(1L);
        PostActionTypesDTO postActionTypesDTO2 = new PostActionTypesDTO();
        assertThat(postActionTypesDTO1).isNotEqualTo(postActionTypesDTO2);
        postActionTypesDTO2.setId(postActionTypesDTO1.getId());
        assertThat(postActionTypesDTO1).isEqualTo(postActionTypesDTO2);
        postActionTypesDTO2.setId(2L);
        assertThat(postActionTypesDTO1).isNotEqualTo(postActionTypesDTO2);
        postActionTypesDTO1.setId(null);
        assertThat(postActionTypesDTO1).isNotEqualTo(postActionTypesDTO2);
    }
}
