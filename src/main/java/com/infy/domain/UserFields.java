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
 * A UserFields.
 */
@Entity
@Table(name = "user_fields")
public class UserFields extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "field_type", nullable = false)
    private String fieldType;

    @NotNull
    @Column(name = "editable", nullable = false)
    private Boolean editable;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "required", nullable = false)
    private Boolean required;

    @NotNull
    @Column(name = "show_on_profile", nullable = false)
    private Boolean showOnProfile;

    @Column(name = "position")
    private Integer position;

    @NotNull
    @Column(name = "show_on_user_card", nullable = false)
    private Boolean showOnUserCard;

    @Column(name = "external_name")
    private String externalName;

    @Column(name = "external_type")
    private String externalType;

    @NotNull
    @Column(name = "searchable", nullable = false)
    private Boolean searchable;

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

    public UserFields name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public UserFields fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean isEditable() {
        return editable;
    }

    public UserFields editable(Boolean editable) {
        this.editable = editable;
        return this;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getDescription() {
        return description;
    }

    public UserFields description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isRequired() {
        return required;
    }

    public UserFields required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isShowOnProfile() {
        return showOnProfile;
    }

    public UserFields showOnProfile(Boolean showOnProfile) {
        this.showOnProfile = showOnProfile;
        return this;
    }

    public void setShowOnProfile(Boolean showOnProfile) {
        this.showOnProfile = showOnProfile;
    }

    public Integer getPosition() {
        return position;
    }

    public UserFields position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isShowOnUserCard() {
        return showOnUserCard;
    }

    public UserFields showOnUserCard(Boolean showOnUserCard) {
        this.showOnUserCard = showOnUserCard;
        return this;
    }

    public void setShowOnUserCard(Boolean showOnUserCard) {
        this.showOnUserCard = showOnUserCard;
    }

    public String getExternalName() {
        return externalName;
    }

    public UserFields externalName(String externalName) {
        this.externalName = externalName;
        return this;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public String getExternalType() {
        return externalType;
    }

    public UserFields externalType(String externalType) {
        this.externalType = externalType;
        return this;
    }

    public void setExternalType(String externalType) {
        this.externalType = externalType;
    }

    public Boolean isSearchable() {
        return searchable;
    }

    public UserFields searchable(Boolean searchable) {
        this.searchable = searchable;
        return this;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserFields)) {
            return false;
        }
        return id != null && id.equals(((UserFields) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFields{" +
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
