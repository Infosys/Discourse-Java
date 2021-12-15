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

public class PostUploadsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostUploadsDTO.class);
        PostUploadsDTO postUploadsDTO1 = new PostUploadsDTO();
        postUploadsDTO1.setId(1L);
        PostUploadsDTO postUploadsDTO2 = new PostUploadsDTO();
        assertThat(postUploadsDTO1).isNotEqualTo(postUploadsDTO2);
        postUploadsDTO2.setId(postUploadsDTO1.getId());
        assertThat(postUploadsDTO1).isEqualTo(postUploadsDTO2);
        postUploadsDTO2.setId(2L);
        assertThat(postUploadsDTO1).isNotEqualTo(postUploadsDTO2);
        postUploadsDTO1.setId(null);
        assertThat(postUploadsDTO1).isNotEqualTo(postUploadsDTO2);
    }
}
