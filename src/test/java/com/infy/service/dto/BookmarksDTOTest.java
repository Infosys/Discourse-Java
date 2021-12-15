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

public class BookmarksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookmarksDTO.class);
        BookmarksDTO bookmarksDTO1 = new BookmarksDTO();
        bookmarksDTO1.setId(1L);
        BookmarksDTO bookmarksDTO2 = new BookmarksDTO();
        assertThat(bookmarksDTO1).isNotEqualTo(bookmarksDTO2);
        bookmarksDTO2.setId(bookmarksDTO1.getId());
        assertThat(bookmarksDTO1).isEqualTo(bookmarksDTO2);
        bookmarksDTO2.setId(2L);
        assertThat(bookmarksDTO1).isNotEqualTo(bookmarksDTO2);
        bookmarksDTO1.setId(null);
        assertThat(bookmarksDTO1).isNotEqualTo(bookmarksDTO2);
    }
}
