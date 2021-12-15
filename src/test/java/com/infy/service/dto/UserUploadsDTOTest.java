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

public class UserUploadsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserUploadsDTO.class);
        UserUploadsDTO userUploadsDTO1 = new UserUploadsDTO();
        userUploadsDTO1.setId(1L);
        UserUploadsDTO userUploadsDTO2 = new UserUploadsDTO();
        assertThat(userUploadsDTO1).isNotEqualTo(userUploadsDTO2);
        userUploadsDTO2.setId(userUploadsDTO1.getId());
        assertThat(userUploadsDTO1).isEqualTo(userUploadsDTO2);
        userUploadsDTO2.setId(2L);
        assertThat(userUploadsDTO1).isNotEqualTo(userUploadsDTO2);
        userUploadsDTO1.setId(null);
        assertThat(userUploadsDTO1).isNotEqualTo(userUploadsDTO2);
    }
}
