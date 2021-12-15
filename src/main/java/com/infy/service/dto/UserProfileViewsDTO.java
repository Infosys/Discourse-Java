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
 * A DTO for the {@link com.infy.domain.UserProfileViews} entity.
 */
public class UserProfileViewsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userProfileId;

    @NotNull
    private Instant viewedAt;

    private String ipAddress;

    private String userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Instant getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Instant viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfileViewsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserProfileViewsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfileViewsDTO{" +
            "id=" + getId() +
            ", userProfileId=" + getUserProfileId() +
            ", viewedAt='" + getViewedAt() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
