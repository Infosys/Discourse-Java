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

public class WatchedWordsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchedWords.class);
        WatchedWords watchedWords1 = new WatchedWords();
        watchedWords1.setId(1L);
        WatchedWords watchedWords2 = new WatchedWords();
        watchedWords2.setId(watchedWords1.getId());
        assertThat(watchedWords1).isEqualTo(watchedWords2);
        watchedWords2.setId(2L);
        assertThat(watchedWords1).isNotEqualTo(watchedWords2);
        watchedWords1.setId(null);
        assertThat(watchedWords1).isNotEqualTo(watchedWords2);
    }
}
