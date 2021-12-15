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

public class TagGroupPermissionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagGroupPermissionsDTO.class);
        TagGroupPermissionsDTO tagGroupPermissionsDTO1 = new TagGroupPermissionsDTO();
        tagGroupPermissionsDTO1.setId(1L);
        TagGroupPermissionsDTO tagGroupPermissionsDTO2 = new TagGroupPermissionsDTO();
        assertThat(tagGroupPermissionsDTO1).isNotEqualTo(tagGroupPermissionsDTO2);
        tagGroupPermissionsDTO2.setId(tagGroupPermissionsDTO1.getId());
        assertThat(tagGroupPermissionsDTO1).isEqualTo(tagGroupPermissionsDTO2);
        tagGroupPermissionsDTO2.setId(2L);
        assertThat(tagGroupPermissionsDTO1).isNotEqualTo(tagGroupPermissionsDTO2);
        tagGroupPermissionsDTO1.setId(null);
        assertThat(tagGroupPermissionsDTO1).isNotEqualTo(tagGroupPermissionsDTO2);
    }
}
