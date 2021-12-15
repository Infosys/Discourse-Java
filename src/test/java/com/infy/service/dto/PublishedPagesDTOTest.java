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

public class PublishedPagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublishedPagesDTO.class);
        PublishedPagesDTO publishedPagesDTO1 = new PublishedPagesDTO();
        publishedPagesDTO1.setId(1L);
        PublishedPagesDTO publishedPagesDTO2 = new PublishedPagesDTO();
        assertThat(publishedPagesDTO1).isNotEqualTo(publishedPagesDTO2);
        publishedPagesDTO2.setId(publishedPagesDTO1.getId());
        assertThat(publishedPagesDTO1).isEqualTo(publishedPagesDTO2);
        publishedPagesDTO2.setId(2L);
        assertThat(publishedPagesDTO1).isNotEqualTo(publishedPagesDTO2);
        publishedPagesDTO1.setId(null);
        assertThat(publishedPagesDTO1).isNotEqualTo(publishedPagesDTO2);
    }
}
