/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.GroupUsers} entity.
 */
public class GroupUsersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long groupId;

    @NotNull
    private String userId;

    @NotNull
    private Boolean owner;

    @NotNull
    private Integer notificationLevel;

    @NotNull
    private Instant firstUnreadPmAt;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public Instant getFirstUnreadPmAt() {
        return firstUnreadPmAt;
    }

    public void setFirstUnreadPmAt(Instant firstUnreadPmAt) {
        this.firstUnreadPmAt = firstUnreadPmAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupUsersDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupUsersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupUsersDTO{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", userId='" + getUserId() + "'" +
            ", owner='" + isOwner() + "'" +
            ", notificationLevel=" + getNotificationLevel() +
            ", firstUnreadPmAt='" + getFirstUnreadPmAt() + "'" +
            "}";
    }
}
