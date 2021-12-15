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

public class AnnouncementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnouncementDTO.class);
        AnnouncementDTO announcmentDTO1 = new AnnouncementDTO();
        announcmentDTO1.setId(1L);
        AnnouncementDTO announcmentDTO2 = new AnnouncementDTO();
        assertThat(announcmentDTO1).isNotEqualTo(announcmentDTO2);
        announcmentDTO2.setId(announcmentDTO1.getId());
        assertThat(announcmentDTO1).isEqualTo(announcmentDTO2);
        announcmentDTO2.setId(2L);
        assertThat(announcmentDTO1).isNotEqualTo(announcmentDTO2);
        announcmentDTO1.setId(null);
        assertThat(announcmentDTO1).isNotEqualTo(announcmentDTO2);
    }
}
