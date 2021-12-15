/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.infy.web.rest.TestUtil;

public class AnnouncementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Announcement.class);
        Announcement announcment1 = new Announcement();
        announcment1.setId(1L);
        Announcement announcment2 = new Announcement();
        announcment2.setId(announcment1.getId());
        assertThat(announcment1).isEqualTo(announcment2);
        announcment2.setId(2L);
        assertThat(announcment1).isNotEqualTo(announcment2);
        announcment1.setId(null);
        assertThat(announcment1).isNotEqualTo(announcment2);
    }
}
