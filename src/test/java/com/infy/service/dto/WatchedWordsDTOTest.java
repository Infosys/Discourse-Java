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

public class WatchedWordsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchedWordsDTO.class);
        WatchedWordsDTO watchedWordsDTO1 = new WatchedWordsDTO();
        watchedWordsDTO1.setId(1L);
        WatchedWordsDTO watchedWordsDTO2 = new WatchedWordsDTO();
        assertThat(watchedWordsDTO1).isNotEqualTo(watchedWordsDTO2);
        watchedWordsDTO2.setId(watchedWordsDTO1.getId());
        assertThat(watchedWordsDTO1).isEqualTo(watchedWordsDTO2);
        watchedWordsDTO2.setId(2L);
        assertThat(watchedWordsDTO1).isNotEqualTo(watchedWordsDTO2);
        watchedWordsDTO1.setId(null);
        assertThat(watchedWordsDTO1).isNotEqualTo(watchedWordsDTO2);
    }
}
