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
import java.util.HashSet;
import java.util.Set;

/**
 * A JavascriptCaches.
 */
@Entity
@Table(name = "javascript_caches")
public class JavascriptCaches extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "theme_field_id")
    private Long themeFieldId;

    @Column(name = "digest")
    private String digest;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "theme_id")
    private Long themeId;

    @OneToMany(mappedBy = "javascriptCaches")
    private Set<ThemeFields> themeFields = new HashSet<>();

    @OneToMany(mappedBy = "javascriptCaches")
    private Set<Themes> themes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeFieldId() {
        return themeFieldId;
    }

    public JavascriptCaches themeFieldId(Long themeFieldId) {
        this.themeFieldId = themeFieldId;
        return this;
    }

    public void setThemeFieldId(Long themeFieldId) {
        this.themeFieldId = themeFieldId;
    }

    public String getDigest() {
        return digest;
    }

    public JavascriptCaches digest(String digest) {
        this.digest = digest;
        return this;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public JavascriptCaches content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getThemeId() {
        return themeId;
    }

    public JavascriptCaches themeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Set<ThemeFields> getThemeFields() {
        return themeFields;
    }

    public JavascriptCaches themeFields(Set<ThemeFields> themeFields) {
        this.themeFields = themeFields;
        return this;
    }

    public JavascriptCaches addThemeFields(ThemeFields themeFields) {
        this.themeFields.add(themeFields);
        themeFields.setJavascriptCaches(this);
        return this;
    }

    public JavascriptCaches removeThemeFields(ThemeFields themeFields) {
        this.themeFields.remove(themeFields);
        themeFields.setJavascriptCaches(null);
        return this;
    }

    public void setThemeFields(Set<ThemeFields> themeFields) {
        this.themeFields = themeFields;
    }

    public Set<Themes> getThemes() {
        return themes;
    }

    public JavascriptCaches themes(Set<Themes> themes) {
        this.themes = themes;
        return this;
    }

    public JavascriptCaches addThemes(Themes themes) {
        this.themes.add(themes);
        themes.setJavascriptCaches(this);
        return this;
    }

    public JavascriptCaches removeThemes(Themes themes) {
        this.themes.remove(themes);
        themes.setJavascriptCaches(null);
        return this;
    }

    public void setThemes(Set<Themes> themes) {
        this.themes = themes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JavascriptCaches)) {
            return false;
        }
        return id != null && id.equals(((JavascriptCaches) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JavascriptCaches{" +
            "id=" + getId() +
            ", themeFieldId=" + getThemeFieldId() +
            ", digest='" + getDigest() + "'" +
            ", content='" + getContent() + "'" +
            ", themeId=" + getThemeId() +
            "}";
    }
}
