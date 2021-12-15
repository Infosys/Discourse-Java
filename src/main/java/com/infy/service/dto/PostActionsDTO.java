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
 * A DTO for the {@link com.infy.domain.PostActions} entity.
 */
public class PostActionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private String userId;

    @NotNull
    private Long postActionTypeId;

    private Instant deletedAt;

    private String deletedById;

    private Long relatedPostId;

    @NotNull
    private Boolean staffTookAction;

    private String deferredById;

    @NotNull
    private Boolean targetsTopic;

    private Instant agreedAt;

    private String agreedById;

    private Instant deferredAt;

    private Instant disagreedAt;

    private String disagreedById;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostActionTypeId() {
        return postActionTypeId;
    }

    public void setPostActionTypeId(Long postActionTypeId) {
        this.postActionTypeId = postActionTypeId;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Long getRelatedPostId() {
        return relatedPostId;
    }

    public void setRelatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
    }

    public Boolean isStaffTookAction() {
        return staffTookAction;
    }

    public void setStaffTookAction(Boolean staffTookAction) {
        this.staffTookAction = staffTookAction;
    }

    public String getDeferredById() {
        return deferredById;
    }

    public void setDeferredById(String deferredById) {
        this.deferredById = deferredById;
    }

    public Boolean isTargetsTopic() {
        return targetsTopic;
    }

    public void setTargetsTopic(Boolean targetsTopic) {
        this.targetsTopic = targetsTopic;
    }

    public Instant getAgreedAt() {
        return agreedAt;
    }

    public void setAgreedAt(Instant agreedAt) {
        this.agreedAt = agreedAt;
    }

    public String getAgreedById() {
        return agreedById;
    }

    public void setAgreedById(String agreedById) {
        this.agreedById = agreedById;
    }

    public Instant getDeferredAt() {
        return deferredAt;
    }

    public void setDeferredAt(Instant deferredAt) {
        this.deferredAt = deferredAt;
    }

    public Instant getDisagreedAt() {
        return disagreedAt;
    }

    public void setDisagreedAt(Instant disagreedAt) {
        this.disagreedAt = disagreedAt;
    }

    public String getDisagreedById() {
        return disagreedById;
    }

    public void setDisagreedById(String disagreedById) {
        this.disagreedById = disagreedById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostActionsDTO)) {
            return false;
        }

        return id != null && id.equals(((PostActionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostActionsDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", userId='" + getUserId() + "'" +
            ", postActionTypeId=" + getPostActionTypeId() +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedById='" + getDeletedById() + "'" +
            ", relatedPostId=" + getRelatedPostId() +
            ", staffTookAction='" + isStaffTookAction() + "'" +
            ", deferredById='" + getDeferredById() + "'" +
            ", targetsTopic='" + isTargetsTopic() + "'" +
            ", agreedAt='" + getAgreedAt() + "'" +
            ", agreedById='" + getAgreedById() + "'" +
            ", deferredAt='" + getDeferredAt() + "'" +
            ", disagreedAt='" + getDisagreedAt() + "'" +
            ", disagreedById='" + getDisagreedById() + "'" +
            "}";
    }
}
