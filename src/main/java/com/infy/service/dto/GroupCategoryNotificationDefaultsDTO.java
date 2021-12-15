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
 * A DTO for the {@link com.infy.domain.GroupCategoryNotificationDefaults} entity.
 */
public class GroupCategoryNotificationDefaultsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long groupId;

    @NotNull
    private Long categoryId;

    @NotNull
    private Integer notificationLevel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupCategoryNotificationDefaultsDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupCategoryNotificationDefaultsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupCategoryNotificationDefaultsDTO{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", categoryId=" + getCategoryId() +
            ", notificationLevel=" + getNotificationLevel() +
            "}";
    }
}
