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
 * A TagGroupPermissions.
 */
@Entity
@Table(name = "tag_group_permissions")
public class TagGroupPermissions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tag_group_id", nullable = false)
    private Long tagGroupId;

    @NotNull
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @NotNull
    @Column(name = "permission_type", nullable = false)
    private Integer permissionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagGroupId() {
        return tagGroupId;
    }

    public TagGroupPermissions tagGroupId(Long tagGroupId) {
        this.tagGroupId = tagGroupId;
        return this;
    }

    public void setTagGroupId(Long tagGroupId) {
        this.tagGroupId = tagGroupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public TagGroupPermissions groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public TagGroupPermissions permissionType(Integer permissionType) {
        this.permissionType = permissionType;
        return this;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagGroupPermissions)) {
            return false;
        }
        return id != null && id.equals(((TagGroupPermissions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagGroupPermissions{" +
            "id=" + getId() +
            ", tagGroupId=" + getTagGroupId() +
            ", groupId=" + getGroupId() +
            ", permissionType=" + getPermissionType() +
            "}";
    }
}
