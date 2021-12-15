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

public class OptimizedImagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptimizedImagesDTO.class);
        OptimizedImagesDTO optimizedImagesDTO1 = new OptimizedImagesDTO();
        optimizedImagesDTO1.setId(1L);
        OptimizedImagesDTO optimizedImagesDTO2 = new OptimizedImagesDTO();
        assertThat(optimizedImagesDTO1).isNotEqualTo(optimizedImagesDTO2);
        optimizedImagesDTO2.setId(optimizedImagesDTO1.getId());
        assertThat(optimizedImagesDTO1).isEqualTo(optimizedImagesDTO2);
        optimizedImagesDTO2.setId(2L);
        assertThat(optimizedImagesDTO1).isNotEqualTo(optimizedImagesDTO2);
        optimizedImagesDTO1.setId(null);
        assertThat(optimizedImagesDTO1).isNotEqualTo(optimizedImagesDTO2);
    }
}
