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

public class BookmarksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookmarks.class);
        Bookmarks bookmarks1 = new Bookmarks();
        bookmarks1.setId(1L);
        Bookmarks bookmarks2 = new Bookmarks();
        bookmarks2.setId(bookmarks1.getId());
        assertThat(bookmarks1).isEqualTo(bookmarks2);
        bookmarks2.setId(2L);
        assertThat(bookmarks1).isNotEqualTo(bookmarks2);
        bookmarks1.setId(null);
        assertThat(bookmarks1).isNotEqualTo(bookmarks2);
    }
}
