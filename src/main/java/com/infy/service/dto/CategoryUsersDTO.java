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
 * A DTO for the {@link com.infy.domain.CategoryUsers} entity.
 */
public class CategoryUsersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long categoryId;

    @NotNull
    private String userId;

    private Integer notificationLevel;

    private Instant lastSeenAt;


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

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryUsersDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryUsersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryUsersDTO{" +
            "id=" + getId() +
            ", categoryId=" + getCategoryId() +
            ", userId='" + getUserId() + "'" +
            ", notificationLevel=" + getNotificationLevel() +
            ", lastSeenAt='" + getLastSeenAt() + "'" +
            "}";
    }
}
