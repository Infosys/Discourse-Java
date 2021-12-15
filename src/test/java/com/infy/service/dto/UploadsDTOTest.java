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

public class UploadsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadsDTO.class);
        UploadsDTO uploadsDTO1 = new UploadsDTO();
        uploadsDTO1.setId(1L);
        UploadsDTO uploadsDTO2 = new UploadsDTO();
        assertThat(uploadsDTO1).isNotEqualTo(uploadsDTO2);
        uploadsDTO2.setId(uploadsDTO1.getId());
        assertThat(uploadsDTO1).isEqualTo(uploadsDTO2);
        uploadsDTO2.setId(2L);
        assertThat(uploadsDTO1).isNotEqualTo(uploadsDTO2);
        uploadsDTO1.setId(null);
        assertThat(uploadsDTO1).isNotEqualTo(uploadsDTO2);
    }
}
