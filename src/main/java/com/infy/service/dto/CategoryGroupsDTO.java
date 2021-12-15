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
 * A DTO for the {@link com.infy.domain.CategoryGroups} entity.
 */
public class CategoryGroupsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long groupId;

    private Integer permissionType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryGroupsDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryGroupsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryGroupsDTO{" +
            "id=" + getId() +
            ", categoryId=" + getCategoryId() +
            ", groupId=" + getGroupId() +
            ", permissionType=" + getPermissionType() +
            "}";
    }
}
