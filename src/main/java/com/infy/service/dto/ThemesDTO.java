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
 * A DTO for the {@link com.infy.domain.Themes} entity.
 */
public class ThemesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String userId;

    @NotNull
    private Integer compilerVersion;

    @NotNull
    private Boolean userSelectable;

    @NotNull
    private Boolean hidden;

    private Long colorSchemeId;

    private Long remoteThemeId;

    @NotNull
    private Boolean componentAvailable;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean autoUpdate;


    private Long javascriptCachesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCompilerVersion() {
        return compilerVersion;
    }

    public void setCompilerVersion(Integer compilerVersion) {
        this.compilerVersion = compilerVersion;
    }

    public Boolean isUserSelectable() {
        return userSelectable;
    }

    public void setUserSelectable(Boolean userSelectable) {
        this.userSelectable = userSelectable;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Long getColorSchemeId() {
        return colorSchemeId;
    }

    public void setColorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }

    public Long getRemoteThemeId() {
        return remoteThemeId;
    }

    public void setRemoteThemeId(Long remoteThemeId) {
        this.remoteThemeId = remoteThemeId;
    }

    public Boolean isComponentAvailable() {
        return componentAvailable;
    }

    public void setComponentAvailable(Boolean componentAvailable) {
        this.componentAvailable = componentAvailable;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public Long getJavascriptCachesId() {
        return javascriptCachesId;
    }

    public void setJavascriptCachesId(Long javascriptCachesId) {
        this.javascriptCachesId = javascriptCachesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemesDTO)) {
            return false;
        }

        return id != null && id.equals(((ThemesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemesDTO{" +
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
            ", javascriptCachesId=" + getJavascriptCachesId() +
            "}";
    }
}
