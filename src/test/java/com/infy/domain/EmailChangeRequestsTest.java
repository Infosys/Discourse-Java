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

public class EmailChangeRequestsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailChangeRequests.class);
        EmailChangeRequests emailChangeRequests1 = new EmailChangeRequests();
        emailChangeRequests1.setId(1L);
        EmailChangeRequests emailChangeRequests2 = new EmailChangeRequests();
        emailChangeRequests2.setId(emailChangeRequests1.getId());
        assertThat(emailChangeRequests1).isEqualTo(emailChangeRequests2);
        emailChangeRequests2.setId(2L);
        assertThat(emailChangeRequests1).isNotEqualTo(emailChangeRequests2);
        emailChangeRequests1.setId(null);
        assertThat(emailChangeRequests1).isNotEqualTo(emailChangeRequests2);
    }
}
