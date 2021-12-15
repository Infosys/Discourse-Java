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
 * A StylesheetCache.
 */
@Entity
@Table(name = "stylesheet_cache")
public class StylesheetCache extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "target", nullable = false)
    private String target;

    @NotNull
    @Column(name = "digest", nullable = false)
    private String digest;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "theme_id", nullable = false)
    private Long themeId;

    @Column(name = "source_map")
    private String sourceMap;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public StylesheetCache target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDigest() {
        return digest;
    }

    public StylesheetCache digest(String digest) {
        this.digest = digest;
        return this;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public StylesheetCache content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getThemeId() {
        return themeId;
    }

    public StylesheetCache themeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getSourceMap() {
        return sourceMap;
    }

    public StylesheetCache sourceMap(String sourceMap) {
        this.sourceMap = sourceMap;
        return this;
    }

    public void setSourceMap(String sourceMap) {
        this.sourceMap = sourceMap;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StylesheetCache)) {
            return false;
        }
        return id != null && id.equals(((StylesheetCache) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StylesheetCache{" +
            "id=" + getId() +
            ", target='" + getTarget() + "'" +
            ", digest='" + getDigest() + "'" +
            ", content='" + getContent() + "'" +
            ", themeId=" + getThemeId() +
            ", sourceMap='" + getSourceMap() + "'" +
            "}";
    }
}
