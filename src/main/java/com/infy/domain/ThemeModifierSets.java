/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ThemeModifierSets.
 */
@Entity
@Table(name = "theme_modifier_sets")
public class ThemeModifierSets extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "theme_id", nullable = false)
    private Long themeId;

    @Column(name = "serialize_topic_excerpts")
    private Boolean serializeTopicExcerpts;

    @Column(name = "csp_extensions")
    private String cspExtensions;

    @Column(name = "svg_icons")
    private String svgIcons;

    @Column(name = "topic_thumbnail_sizes")
    private String topicThumbnailSizes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public ThemeModifierSets themeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Boolean isSerializeTopicExcerpts() {
        return serializeTopicExcerpts;
    }

    public ThemeModifierSets serializeTopicExcerpts(Boolean serializeTopicExcerpts) {
        this.serializeTopicExcerpts = serializeTopicExcerpts;
        return this;
    }

    public void setSerializeTopicExcerpts(Boolean serializeTopicExcerpts) {
        this.serializeTopicExcerpts = serializeTopicExcerpts;
    }

    public String getCspExtensions() {
        return cspExtensions;
    }

    public ThemeModifierSets cspExtensions(String cspExtensions) {
        this.cspExtensions = cspExtensions;
        return this;
    }

    public void setCspExtensions(String cspExtensions) {
        this.cspExtensions = cspExtensions;
    }

    public String getSvgIcons() {
        return svgIcons;
    }

    public ThemeModifierSets svgIcons(String svgIcons) {
        this.svgIcons = svgIcons;
        return this;
    }

    public void setSvgIcons(String svgIcons) {
        this.svgIcons = svgIcons;
    }

    public String getTopicThumbnailSizes() {
        return topicThumbnailSizes;
    }

    public ThemeModifierSets topicThumbnailSizes(String topicThumbnailSizes) {
        this.topicThumbnailSizes = topicThumbnailSizes;
        return this;
    }

    public void setTopicThumbnailSizes(String topicThumbnailSizes) {
        this.topicThumbnailSizes = topicThumbnailSizes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeModifierSets)) {
            return false;
        }
        return id != null && id.equals(((ThemeModifierSets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeModifierSets{" +
            "id=" + getId() +
            ", themeId=" + getThemeId() +
            ", serializeTopicExcerpts='" + isSerializeTopicExcerpts() + "'" +
            ", cspExtensions='" + getCspExtensions() + "'" +
            ", svgIcons='" + getSvgIcons() + "'" +
            ", topicThumbnailSizes='" + getTopicThumbnailSizes() + "'" +
            "}";
    }
}
