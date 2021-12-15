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
import java.time.Instant;

/**
 * A UserAvatars.
 */
@Entity
@Table(name = "user_avatars")
public class UserAvatars extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "custom_upload_id")
    private Long customUploadId;

    @Column(name = "gravatar_upload_id")
    private Long gravatarUploadId;

    @Column(name = "last_gravatar_download_attempt")
    private Instant lastGravatarDownloadAttempt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserAvatars userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCustomUploadId() {
        return customUploadId;
    }

    public UserAvatars customUploadId(Long customUploadId) {
        this.customUploadId = customUploadId;
        return this;
    }

    public void setCustomUploadId(Long customUploadId) {
        this.customUploadId = customUploadId;
    }

    public Long getGravatarUploadId() {
        return gravatarUploadId;
    }

    public UserAvatars gravatarUploadId(Long gravatarUploadId) {
        this.gravatarUploadId = gravatarUploadId;
        return this;
    }

    public void setGravatarUploadId(Long gravatarUploadId) {
        this.gravatarUploadId = gravatarUploadId;
    }

    public Instant getLastGravatarDownloadAttempt() {
        return lastGravatarDownloadAttempt;
    }

    public UserAvatars lastGravatarDownloadAttempt(Instant lastGravatarDownloadAttempt) {
        this.lastGravatarDownloadAttempt = lastGravatarDownloadAttempt;
        return this;
    }

    public void setLastGravatarDownloadAttempt(Instant lastGravatarDownloadAttempt) {
        this.lastGravatarDownloadAttempt = lastGravatarDownloadAttempt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserAvatars updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAvatars)) {
            return false;
        }
        return id != null && id.equals(((UserAvatars) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAvatars{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", customUploadId=" + getCustomUploadId() +
            ", gravatarUploadId=" + getGravatarUploadId() +
            ", lastGravatarDownloadAttempt='" + getLastGravatarDownloadAttempt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
