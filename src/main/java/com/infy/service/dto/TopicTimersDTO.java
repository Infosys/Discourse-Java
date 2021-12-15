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
 * A DTO for the {@link com.infy.domain.TopicTimers} entity.
 */
public class TopicTimersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant executeAt;

    @NotNull
    private Integer statusType;

    @NotNull
    private String userId;

    @NotNull
    private Long topicId;

    @NotNull
    private Boolean basedOnLastPost;

    private Instant deletedAt;

    private String deletedById;

    private Long categoryId;

    private Boolean publicType;

    private Integer duration;

    private Integer durationMinutes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Instant executeAt) {
        this.executeAt = executeAt;
    }

    public Integer getStatusType() {
        return statusType;
    }

    public void setStatusType(Integer statusType) {
        this.statusType = statusType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean isBasedOnLastPost() {
        return basedOnLastPost;
    }

    public void setBasedOnLastPost(Boolean basedOnLastPost) {
        this.basedOnLastPost = basedOnLastPost;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isPublicType() {
        return publicType;
    }

    public void setPublicType(Boolean publicType) {
        this.publicType = publicType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicTimersDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicTimersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicTimersDTO{" +
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
