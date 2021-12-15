/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Themes.
 */
@Entity
@Table(name = "themes")
public class Themes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "compiler_version", nullable = false)
    private Integer compilerVersion;

    @NotNull
    @Column(name = "user_selectable", nullable = false)
    private Boolean userSelectable;

    @NotNull
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;

    @Column(name = "color_scheme_id")
    private Long colorSchemeId;

    @Column(name = "remote_theme_id")
    private Long remoteThemeId;

    @NotNull
    @Column(name = "component_available", nullable = false)
    private Boolean componentAvailable;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "auto_update", nullable = false)
    private Boolean autoUpdate;

    @ManyToOne
    @JsonIgnoreProperties(value = "themes", allowSetters = true)
    private JavascriptCaches javascriptCaches;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Themes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public Themes userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCompilerVersion() {
        return compilerVersion;
    }

    public Themes compilerVersion(Integer compilerVersion) {
        this.compilerVersion = compilerVersion;
        return this;
    }

    public void setCompilerVersion(Integer compilerVersion) {
        this.compilerVersion = compilerVersion;
    }

    public Boolean isUserSelectable() {
        return userSelectable;
    }

    public Themes userSelectable(Boolean userSelectable) {
        this.userSelectable = userSelectable;
        return this;
    }

    public void setUserSelectable(Boolean userSelectable) {
        this.userSelectable = userSelectable;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public Themes hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Long getColorSchemeId() {
        return colorSchemeId;
    }

    public Themes colorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
        return this;
    }

    public void setColorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }

    public Long getRemoteThemeId() {
        return remoteThemeId;
    }

    public Themes remoteThemeId(Long remoteThemeId) {
        this.remoteThemeId = remoteThemeId;
        return this;
    }

    public void setRemoteThemeId(Long remoteThemeId) {
        this.remoteThemeId = remoteThemeId;
    }

    public Boolean isComponentAvailable() {
        return componentAvailable;
    }

    public Themes componentAvailable(Boolean componentAvailable) {
        this.componentAvailable = componentAvailable;
        return this;
    }

    public void setComponentAvailable(Boolean componentAvailable) {
        this.componentAvailable = componentAvailable;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Themes enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isAutoUpdate() {
        return autoUpdate;
    }

    public Themes autoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        return this;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public JavascriptCaches getJavascriptCaches() {
        return javascriptCaches;
    }

    public Themes javascriptCaches(JavascriptCaches javascriptCaches) {
        this.javascriptCaches = javascriptCaches;
        return this;
    }

    public void setJavascriptCaches(JavascriptCaches javascriptCaches) {
        this.javascriptCaches = javascriptCaches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Themes)) {
            return false;
        }
        return id != null && id.equals(((Themes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Themes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userId='" + getUserId() + "'" +
            ", compilerVersion=" + getCompilerVersion() +
            ", userSelectable='" + isUserSelectable() + "'" +
            ", hidden='" + isHidden() + "'" +
            ", colorSchemeId=" + getColorSchemeId() +
            ", remoteThemeId=" + getRemoteThemeId() +
            ", componentAvailable='" + isComponentAvailable() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", autoUpdate='" + isAutoUpdate() + "'" +
            "}";
    }
}
