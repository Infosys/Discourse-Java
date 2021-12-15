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
 * A DTO for the {@link com.infy.domain.ThemeModifierSets} entity.
 */
public class ThemeModifierSetsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long themeId;

    private Boolean serializeTopicExcerpts;

    private String cspExtensions;

    private String svgIcons;

    private String topicThumbnailSizes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Boolean isSerializeTopicExcerpts() {
        return serializeTopicExcerpts;
    }

    public void setSerializeTopicExcerpts(Boolean serializeTopicExcerpts) {
        this.serializeTopicExcerpts = serializeTopicExcerpts;
    }

    public String getCspExtensions() {
        return cspExtensions;
    }

    public void setCspExtensions(String cspExtensions) {
        this.cspExtensions = cspExtensions;
    }

    public String getSvgIcons() {
        return svgIcons;
    }

    public void setSvgIcons(String svgIcons) {
        this.svgIcons = svgIcons;
    }

    public String getTopicThumbnailSizes() {
        return topicThumbnailSizes;
    }

    public void setTopicThumbnailSizes(String topicThumbnailSizes) {
        this.topicThumbnailSizes = topicThumbnailSizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeModifierSetsDTO)) {
            return false;
        }

        return id != null && id.equals(((ThemeModifierSetsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeModifierSetsDTO{" +
            "id=" + getId() +
            ", themeId=" + getThemeId() +
            ", serializeTopicExcerpts='" + isSerializeTopicExcerpts() + "'" +
            ", cspExtensions='" + getCspExtensions() + "'" +
            ", svgIcons='" + getSvgIcons() + "'" +
            ", topicThumbnailSizes='" + getTopicThumbnailSizes() + "'" +
            "}";
    }
}
