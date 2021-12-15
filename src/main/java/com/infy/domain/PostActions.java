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
 * A PostActions.
 */
@Entity
@Table(name = "post_actions")
public class PostActions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "post_action_type_id", nullable = false)
    private Long postActionTypeId;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "related_post_id")
    private Long relatedPostId;

    @NotNull
    @Column(name = "staff_took_action", nullable = false)
    private Boolean staffTookAction;

    @Column(name = "deferred_by_id")
    private String deferredById;

    @NotNull
    @Column(name = "targets_topic", nullable = false)
    private Boolean targetsTopic;

    @Column(name = "agreed_at")
    private Instant agreedAt;

    @Column(name = "agreed_by_id")
    private String agreedById;

    @Column(name = "deferred_at")
    private Instant deferredAt;

    @Column(name = "disagreed_at")
    private Instant disagreedAt;

    @Column(name = "disagreed_by_id")
    private String disagreedById;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public PostActions postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public PostActions userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostActionTypeId() {
        return postActionTypeId;
    }

    public PostActions postActionTypeId(Long postActionTypeId) {
        this.postActionTypeId = postActionTypeId;
        return this;
    }

    public void setPostActionTypeId(Long postActionTypeId) {
        this.postActionTypeId = postActionTypeId;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public PostActions deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public PostActions deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Long getRelatedPostId() {
        return relatedPostId;
    }

    public PostActions relatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
        return this;
    }

    public void setRelatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
    }

    public Boolean isStaffTookAction() {
        return staffTookAction;
    }

    public PostActions staffTookAction(Boolean staffTookAction) {
        this.staffTookAction = staffTookAction;
        return this;
    }

    public void setStaffTookAction(Boolean staffTookAction) {
        this.staffTookAction = staffTookAction;
    }

    public String getDeferredById() {
        return deferredById;
    }

    public PostActions deferredById(String deferredById) {
        this.deferredById = deferredById;
        return this;
    }

    public void setDeferredById(String deferredById) {
        this.deferredById = deferredById;
    }

    public Boolean isTargetsTopic() {
        return targetsTopic;
    }

    public PostActions targetsTopic(Boolean targetsTopic) {
        this.targetsTopic = targetsTopic;
        return this;
    }

    public void setTargetsTopic(Boolean targetsTopic) {
        this.targetsTopic = targetsTopic;
    }

    public Instant getAgreedAt() {
        return agreedAt;
    }

    public PostActions agreedAt(Instant agreedAt) {
        this.agreedAt = agreedAt;
        return this;
    }

    public void setAgreedAt(Instant agreedAt) {
        this.agreedAt = agreedAt;
    }

    public String getAgreedById() {
        return agreedById;
    }

    public PostActions agreedById(String agreedById) {
        this.agreedById = agreedById;
        return this;
    }

    public void setAgreedById(String agreedById) {
        this.agreedById = agreedById;
    }

    public Instant getDeferredAt() {
        return deferredAt;
    }

    public PostActions deferredAt(Instant deferredAt) {
        this.deferredAt = deferredAt;
        return this;
    }

    public void setDeferredAt(Instant deferredAt) {
        this.deferredAt = deferredAt;
    }

    public Instant getDisagreedAt() {
        return disagreedAt;
    }

    public PostActions disagreedAt(Instant disagreedAt) {
        this.disagreedAt = disagreedAt;
        return this;
    }

    public void setDisagreedAt(Instant disagreedAt) {
        this.disagreedAt = disagreedAt;
    }

    public String getDisagreedById() {
        return disagreedById;
    }

    public PostActions disagreedById(String disagreedById) {
        this.disagreedById = disagreedById;
        return this;
    }

    public void setDisagreedById(String disagreedById) {
        this.disagreedById = disagreedById;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostActions)) {
            return false;
        }
        return id != null && id.equals(((PostActions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostActions{" +
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
