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
 * A DTO for the {@link com.infy.domain.UserAvatars} entity.
 */
public class UserAvatarsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    private Long customUploadId;

    private Long gravatarUploadId;

    private Instant lastGravatarDownloadAttempt;

    @NotNull
    private Instant updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCustomUploadId() {
        return customUploadId;
    }

    public void setCustomUploadId(Long customUploadId) {
        this.customUploadId = customUploadId;
    }

    public Long getGravatarUploadId() {
        return gravatarUploadId;
    }

    public void setGravatarUploadId(Long gravatarUploadId) {
        this.gravatarUploadId = gravatarUploadId;
    }

    public Instant getLastGravatarDownloadAttempt() {
        return lastGravatarDownloadAttempt;
    }

    public void setLastGravatarDownloadAttempt(Instant lastGravatarDownloadAttempt) {
        this.lastGravatarDownloadAttempt = lastGravatarDownloadAttempt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAvatarsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAvatarsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAvatarsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", customUploadId=" + getCustomUploadId() +
            ", gravatarUploadId=" + getGravatarUploadId() +
            ", lastGravatarDownloadAttempt='" + getLastGravatarDownloadAttempt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
