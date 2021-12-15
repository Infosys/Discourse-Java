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

public class WebCrawlerRequestsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebCrawlerRequestsDTO.class);
        WebCrawlerRequestsDTO webCrawlerRequestsDTO1 = new WebCrawlerRequestsDTO();
        webCrawlerRequestsDTO1.setId(1L);
        WebCrawlerRequestsDTO webCrawlerRequestsDTO2 = new WebCrawlerRequestsDTO();
        assertThat(webCrawlerRequestsDTO1).isNotEqualTo(webCrawlerRequestsDTO2);
        webCrawlerRequestsDTO2.setId(webCrawlerRequestsDTO1.getId());
        assertThat(webCrawlerRequestsDTO1).isEqualTo(webCrawlerRequestsDTO2);
        webCrawlerRequestsDTO2.setId(2L);
        assertThat(webCrawlerRequestsDTO1).isNotEqualTo(webCrawlerRequestsDTO2);
        webCrawlerRequestsDTO1.setId(null);
        assertThat(webCrawlerRequestsDTO1).isNotEqualTo(webCrawlerRequestsDTO2);
    }
}
