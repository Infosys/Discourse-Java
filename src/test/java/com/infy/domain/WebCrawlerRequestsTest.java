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

public class WebCrawlerRequestsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebCrawlerRequests.class);
        WebCrawlerRequests webCrawlerRequests1 = new WebCrawlerRequests();
        webCrawlerRequests1.setId(1L);
        WebCrawlerRequests webCrawlerRequests2 = new WebCrawlerRequests();
        webCrawlerRequests2.setId(webCrawlerRequests1.getId());
        assertThat(webCrawlerRequests1).isEqualTo(webCrawlerRequests2);
        webCrawlerRequests2.setId(2L);
        assertThat(webCrawlerRequests1).isNotEqualTo(webCrawlerRequests2);
        webCrawlerRequests1.setId(null);
        assertThat(webCrawlerRequests1).isNotEqualTo(webCrawlerRequests2);
    }
}
