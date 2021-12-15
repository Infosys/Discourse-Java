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

public class OptimizedImagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptimizedImages.class);
        OptimizedImages optimizedImages1 = new OptimizedImages();
        optimizedImages1.setId(1L);
        OptimizedImages optimizedImages2 = new OptimizedImages();
        optimizedImages2.setId(optimizedImages1.getId());
        assertThat(optimizedImages1).isEqualTo(optimizedImages2);
        optimizedImages2.setId(2L);
        assertThat(optimizedImages1).isNotEqualTo(optimizedImages2);
        optimizedImages1.setId(null);
        assertThat(optimizedImages1).isNotEqualTo(optimizedImages2);
    }
}
