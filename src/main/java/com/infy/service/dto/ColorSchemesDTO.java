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
 * A DTO for the {@link com.infy.domain.ColorSchemes} entity.
 */
public class ColorSchemesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer version;

    @NotNull
    private Boolean viaWizard;

    private String baseSchemeId;

    private Long themeId;

    @NotNull
    private Boolean userSelectable;


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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean isViaWizard() {
        return viaWizard;
    }

    public void setViaWizard(Boolean viaWizard) {
        this.viaWizard = viaWizard;
    }

    public String getBaseSchemeId() {
        return baseSchemeId;
    }

    public void setBaseSchemeId(String baseSchemeId) {
        this.baseSchemeId = baseSchemeId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Boolean isUserSelectable() {
        return userSelectable;
    }

    public void setUserSelectable(Boolean userSelectable) {
        this.userSelectable = userSelectable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColorSchemesDTO)) {
            return false;
        }

        return id != null && id.equals(((ColorSchemesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColorSchemesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", version=" + getVersion() +
            ", viaWizard='" + isViaWizard() + "'" +
            ", baseSchemeId='" + getBaseSchemeId() + "'" +
            ", themeId=" + getThemeId() +
            ", userSelectable='" + isUserSelectable() + "'" +
            "}";
    }
}
