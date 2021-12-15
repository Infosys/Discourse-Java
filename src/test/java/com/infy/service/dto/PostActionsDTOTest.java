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

public class PostActionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostActionsDTO.class);
        PostActionsDTO postActionsDTO1 = new PostActionsDTO();
        postActionsDTO1.setId(1L);
        PostActionsDTO postActionsDTO2 = new PostActionsDTO();
        assertThat(postActionsDTO1).isNotEqualTo(postActionsDTO2);
        postActionsDTO2.setId(postActionsDTO1.getId());
        assertThat(postActionsDTO1).isEqualTo(postActionsDTO2);
        postActionsDTO2.setId(2L);
        assertThat(postActionsDTO1).isNotEqualTo(postActionsDTO2);
        postActionsDTO1.setId(null);
        assertThat(postActionsDTO1).isNotEqualTo(postActionsDTO2);
    }
}
