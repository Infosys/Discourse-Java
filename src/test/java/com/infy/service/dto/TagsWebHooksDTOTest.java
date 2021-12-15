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

public class TagsWebHooksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagsWebHooksDTO.class);
        TagsWebHooksDTO tagsWebHooksDTO1 = new TagsWebHooksDTO();
        tagsWebHooksDTO1.setId(1L);
        TagsWebHooksDTO tagsWebHooksDTO2 = new TagsWebHooksDTO();
        assertThat(tagsWebHooksDTO1).isNotEqualTo(tagsWebHooksDTO2);
        tagsWebHooksDTO2.setId(tagsWebHooksDTO1.getId());
        assertThat(tagsWebHooksDTO1).isEqualTo(tagsWebHooksDTO2);
        tagsWebHooksDTO2.setId(2L);
        assertThat(tagsWebHooksDTO1).isNotEqualTo(tagsWebHooksDTO2);
        tagsWebHooksDTO1.setId(null);
        assertThat(tagsWebHooksDTO1).isNotEqualTo(tagsWebHooksDTO2);
    }
}
