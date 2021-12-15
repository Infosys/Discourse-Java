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

public class ApplicationRequestsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationRequests.class);
        ApplicationRequests applicationRequests1 = new ApplicationRequests();
        applicationRequests1.setId(1L);
        ApplicationRequests applicationRequests2 = new ApplicationRequests();
        applicationRequests2.setId(applicationRequests1.getId());
        assertThat(applicationRequests1).isEqualTo(applicationRequests2);
        applicationRequests2.setId(2L);
        assertThat(applicationRequests1).isNotEqualTo(applicationRequests2);
        applicationRequests1.setId(null);
        assertThat(applicationRequests1).isNotEqualTo(applicationRequests2);
    }
}
