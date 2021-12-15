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

public class IncomingDomainsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingDomainsDTO.class);
        IncomingDomainsDTO incomingDomainsDTO1 = new IncomingDomainsDTO();
        incomingDomainsDTO1.setId(1L);
        IncomingDomainsDTO incomingDomainsDTO2 = new IncomingDomainsDTO();
        assertThat(incomingDomainsDTO1).isNotEqualTo(incomingDomainsDTO2);
        incomingDomainsDTO2.setId(incomingDomainsDTO1.getId());
        assertThat(incomingDomainsDTO1).isEqualTo(incomingDomainsDTO2);
        incomingDomainsDTO2.setId(2L);
        assertThat(incomingDomainsDTO1).isNotEqualTo(incomingDomainsDTO2);
        incomingDomainsDTO1.setId(null);
        assertThat(incomingDomainsDTO1).isNotEqualTo(incomingDomainsDTO2);
    }
}
