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
 * A DTO for the {@link com.infy.domain.StylesheetCache} entity.
 */
public class StylesheetCacheDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String target;

    @NotNull
    private String digest;

    @NotNull
    private String content;

    @NotNull
    private Long themeId;

    private String sourceMap;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public String getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(String sourceMap) {
        this.sourceMap = sourceMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StylesheetCacheDTO)) {
            return false;
        }

        return id != null && id.equals(((StylesheetCacheDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StylesheetCacheDTO{" +
            "id=" + getId() +
            ", target='" + getTarget() + "'" +
            ", digest='" + getDigest() + "'" +
            ", content='" + getContent() + "'" +
            ", themeId=" + getThemeId() +
            ", sourceMap='" + getSourceMap() + "'" +
            "}";
    }
}
