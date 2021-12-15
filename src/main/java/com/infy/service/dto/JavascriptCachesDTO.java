/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.JavascriptCaches} entity.
 */
public class JavascriptCachesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long themeFieldId;

    private String digest;

    @NotNull
    private String content;

    private Long themeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeFieldId() {
        return themeFieldId;
    }

    public void setThemeFieldId(Long themeFieldId) {
        this.themeFieldId = themeFieldId;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JavascriptCachesDTO)) {
            return false;
        }

        return id != null && id.equals(((JavascriptCachesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JavascriptCachesDTO{" +
            "id=" + getId() +
            ", themeFieldId=" + getThemeFieldId() +
            ", digest='" + getDigest() + "'" +
            ", content='" + getContent() + "'" +
            ", themeId=" + getThemeId() +
            "}";
    }
}
