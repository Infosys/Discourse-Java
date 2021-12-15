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

public class OnceoffLogsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OnceoffLogs.class);
        OnceoffLogs onceoffLogs1 = new OnceoffLogs();
        onceoffLogs1.setId(1L);
        OnceoffLogs onceoffLogs2 = new OnceoffLogs();
        onceoffLogs2.setId(onceoffLogs1.getId());
        assertThat(onceoffLogs1).isEqualTo(onceoffLogs2);
        onceoffLogs2.setId(2L);
        assertThat(onceoffLogs1).isNotEqualTo(onceoffLogs2);
        onceoffLogs1.setId(null);
        assertThat(onceoffLogs1).isNotEqualTo(onceoffLogs2);
    }
}
