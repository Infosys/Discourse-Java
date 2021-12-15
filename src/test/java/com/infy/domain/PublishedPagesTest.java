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

public class PublishedPagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublishedPages.class);
        PublishedPages publishedPages1 = new PublishedPages();
        publishedPages1.setId(1L);
        PublishedPages publishedPages2 = new PublishedPages();
        publishedPages2.setId(publishedPages1.getId());
        assertThat(publishedPages1).isEqualTo(publishedPages2);
        publishedPages2.setId(2L);
        assertThat(publishedPages1).isNotEqualTo(publishedPages2);
        publishedPages1.setId(null);
        assertThat(publishedPages1).isNotEqualTo(publishedPages2);
    }
}
