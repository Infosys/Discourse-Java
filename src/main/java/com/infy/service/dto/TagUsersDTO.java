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
 * A DTO for the {@link com.infy.domain.TagUsers} entity.
 */
public class TagUsersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long tagId;

    @NotNull
    private String userId;

    @NotNull
    private Integer notificationLevel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        if (!(o instanceof TagUsersDTO)) {
            return false;
        }

        return id != null && id.equals(((TagUsersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagUsersDTO{" +
            "id=" + getId() +
            ", tagId=" + getTagId() +
            ", userId='" + getUserId() + "'" +
            ", notificationLevel=" + getNotificationLevel() +
            "}";
    }
}
