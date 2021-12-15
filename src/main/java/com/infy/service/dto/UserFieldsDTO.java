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
 * A DTO for the {@link com.infy.domain.UserFields} entity.
 */
public class UserFieldsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String fieldType;

    @NotNull
    private Boolean editable;

    @NotNull
    private String description;

    @NotNull
    private Boolean required;

    @NotNull
    private Boolean showOnProfile;

    private Integer position;

    @NotNull
    private Boolean showOnUserCard;

    private String externalName;

    private String externalType;

    @NotNull
    private Boolean searchable;


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

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean isEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isShowOnProfile() {
        return showOnProfile;
    }

    public void setShowOnProfile(Boolean showOnProfile) {
        this.showOnProfile = showOnProfile;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isShowOnUserCard() {
        return showOnUserCard;
    }

    public void setShowOnUserCard(Boolean showOnUserCard) {
        this.showOnUserCard = showOnUserCard;
    }

    public String getExternalName() {
        return externalName;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public String getExternalType() {
        return externalType;
    }

    public void setExternalType(String externalType) {
        this.externalType = externalType;
    }

    public Boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserFieldsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserFieldsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFieldsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", editable='" + isEditable() + "'" +
            ", description='" + getDescription() + "'" +
            ", required='" + isRequired() + "'" +
            ", showOnProfile='" + isShowOnProfile() + "'" +
            ", position=" + getPosition() +
            ", showOnUserCard='" + isShowOnUserCard() + "'" +
            ", externalName='" + getExternalName() + "'" +
            ", externalType='" + getExternalType() + "'" +
            ", searchable='" + isSearchable() + "'" +
            "}";
    }
}
