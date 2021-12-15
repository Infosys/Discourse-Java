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
 * A TopicTimers.
 */
@Entity
@Table(name = "topic_timers")
public class TopicTimers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "execute_at", nullable = false)
    private Instant executeAt;

    @NotNull
    @Column(name = "status_type", nullable = false)
    private Integer statusType;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "based_on_last_post", nullable = false)
    private Boolean basedOnLastPost;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "public_type")
    private Boolean publicType;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExecuteAt() {
        return executeAt;
    }

    public TopicTimers executeAt(Instant executeAt) {
        this.executeAt = executeAt;
        return this;
    }

    public void setExecuteAt(Instant executeAt) {
        this.executeAt = executeAt;
    }

    public Integer getStatusType() {
        return statusType;
    }

    public TopicTimers statusType(Integer statusType) {
        this.statusType = statusType;
        return this;
    }

    public void setStatusType(Integer statusType) {
        this.statusType = statusType;
    }

    public String getUserId() {
        return userId;
    }

    public TopicTimers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public TopicTimers topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean isBasedOnLastPost() {
        return basedOnLastPost;
    }

    public TopicTimers basedOnLastPost(Boolean basedOnLastPost) {
        this.basedOnLastPost = basedOnLastPost;
        return this;
    }

    public void setBasedOnLastPost(Boolean basedOnLastPost) {
        this.basedOnLastPost = basedOnLastPost;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public TopicTimers deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public TopicTimers deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public TopicTimers categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isPublicType() {
        return publicType;
    }

    public TopicTimers publicType(Boolean publicType) {
        this.publicType = publicType;
        return this;
    }

    public void setPublicType(Boolean publicType) {
        this.publicType = publicType;
    }

    public Integer getDuration() {
        return duration;
    }

    public TopicTimers duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public TopicTimers durationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicTimers)) {
            return false;
        }
        return id != null && id.equals(((TopicTimers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicTimers{" +
            "id=" + getId() +
            ", executeAt='" + getExecuteAt() + "'" +
            ", statusType=" + getStatusType() +
            ", userId='" + getUserId() + "'" +
            ", topicId=" + getTopicId() +
            ", basedOnLastPost='" + isBasedOnLastPost() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedById='" + getDeletedById() + "'" +
            ", categoryId=" + getCategoryId() +
            ", publicType='" + isPublicType() + "'" +
            ", duration=" + getDuration() +
            ", durationMinutes=" + getDurationMinutes() +
            "}";
    }
}
