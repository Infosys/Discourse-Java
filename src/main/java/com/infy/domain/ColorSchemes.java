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
 * A ColorSchemes.
 */
@Entity
@Table(name = "color_schemes")
public class ColorSchemes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "via_wizard", nullable = false)
    private Boolean viaWizard;

    @Column(name = "base_scheme_id")
    private String baseSchemeId;

    @Column(name = "theme_id")
    private Long themeId;

    @NotNull
    @Column(name = "user_selectable", nullable = false)
    private Boolean userSelectable;

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

    public ColorSchemes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public ColorSchemes version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean isViaWizard() {
        return viaWizard;
    }

    public ColorSchemes viaWizard(Boolean viaWizard) {
        this.viaWizard = viaWizard;
        return this;
    }

    public void setViaWizard(Boolean viaWizard) {
        this.viaWizard = viaWizard;
    }

    public String getBaseSchemeId() {
        return baseSchemeId;
    }

    public ColorSchemes baseSchemeId(String baseSchemeId) {
        this.baseSchemeId = baseSchemeId;
        return this;
    }

    public void setBaseSchemeId(String baseSchemeId) {
        this.baseSchemeId = baseSchemeId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public ColorSchemes themeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Boolean isUserSelectable() {
        return userSelectable;
    }

    public ColorSchemes userSelectable(Boolean userSelectable) {
        this.userSelectable = userSelectable;
        return this;
    }

    public void setUserSelectable(Boolean userSelectable) {
        this.userSelectable = userSelectable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColorSchemes)) {
            return false;
        }
        return id != null && id.equals(((ColorSchemes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColorSchemes{" +
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
