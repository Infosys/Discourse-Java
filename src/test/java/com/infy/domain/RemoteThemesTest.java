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

public class RemoteThemesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemoteThemes.class);
        RemoteThemes remoteThemes1 = new RemoteThemes();
        remoteThemes1.setId(1L);
        RemoteThemes remoteThemes2 = new RemoteThemes();
        remoteThemes2.setId(remoteThemes1.getId());
        assertThat(remoteThemes1).isEqualTo(remoteThemes2);
        remoteThemes2.setId(2L);
        assertThat(remoteThemes1).isNotEqualTo(remoteThemes2);
        remoteThemes1.setId(null);
        assertThat(remoteThemes1).isNotEqualTo(remoteThemes2);
    }
}
