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

public class OnceoffLogsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OnceoffLogsDTO.class);
        OnceoffLogsDTO onceoffLogsDTO1 = new OnceoffLogsDTO();
        onceoffLogsDTO1.setId(1L);
        OnceoffLogsDTO onceoffLogsDTO2 = new OnceoffLogsDTO();
        assertThat(onceoffLogsDTO1).isNotEqualTo(onceoffLogsDTO2);
        onceoffLogsDTO2.setId(onceoffLogsDTO1.getId());
        assertThat(onceoffLogsDTO1).isEqualTo(onceoffLogsDTO2);
        onceoffLogsDTO2.setId(2L);
        assertThat(onceoffLogsDTO1).isNotEqualTo(onceoffLogsDTO2);
        onceoffLogsDTO1.setId(null);
        assertThat(onceoffLogsDTO1).isNotEqualTo(onceoffLogsDTO2);
    }
}
