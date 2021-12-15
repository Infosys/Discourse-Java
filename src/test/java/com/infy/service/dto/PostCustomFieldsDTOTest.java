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

public class PostCustomFieldsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCustomFieldsDTO.class);
        PostCustomFieldsDTO postCustomFieldsDTO1 = new PostCustomFieldsDTO();
        postCustomFieldsDTO1.setId(1L);
        PostCustomFieldsDTO postCustomFieldsDTO2 = new PostCustomFieldsDTO();
        assertThat(postCustomFieldsDTO1).isNotEqualTo(postCustomFieldsDTO2);
        postCustomFieldsDTO2.setId(postCustomFieldsDTO1.getId());
        assertThat(postCustomFieldsDTO1).isEqualTo(postCustomFieldsDTO2);
        postCustomFieldsDTO2.setId(2L);
        assertThat(postCustomFieldsDTO1).isNotEqualTo(postCustomFieldsDTO2);
        postCustomFieldsDTO1.setId(null);
        assertThat(postCustomFieldsDTO1).isNotEqualTo(postCustomFieldsDTO2);
    }
}
