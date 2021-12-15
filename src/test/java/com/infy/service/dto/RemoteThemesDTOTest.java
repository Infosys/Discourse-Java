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

public class RemoteThemesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemoteThemesDTO.class);
        RemoteThemesDTO remoteThemesDTO1 = new RemoteThemesDTO();
        remoteThemesDTO1.setId(1L);
        RemoteThemesDTO remoteThemesDTO2 = new RemoteThemesDTO();
        assertThat(remoteThemesDTO1).isNotEqualTo(remoteThemesDTO2);
        remoteThemesDTO2.setId(remoteThemesDTO1.getId());
        assertThat(remoteThemesDTO1).isEqualTo(remoteThemesDTO2);
        remoteThemesDTO2.setId(2L);
        assertThat(remoteThemesDTO1).isNotEqualTo(remoteThemesDTO2);
        remoteThemesDTO1.setId(null);
        assertThat(remoteThemesDTO1).isNotEqualTo(remoteThemesDTO2);
    }
}
