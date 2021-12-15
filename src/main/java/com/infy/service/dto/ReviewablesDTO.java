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
 * A DTO for the {@link com.infy.domain.Reviewables} entity.
 */
public class ReviewablesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String type;

    @NotNull
    private Integer status;

    @NotNull
    private Boolean reviewableByModerator;

    private Long reviewableByGroupId;

    private Long categoryId;

    private Long topicId;

    @NotNull
    private Double score;

    @NotNull
    private Boolean potentialSpam;

    private Long targetId;

    private String targetType;

    private String targetCreatedById;

    private String payload;

    @NotNull
    private Integer version;

    private Instant latestScore;

    @NotNull
    private Boolean forceReview;

    private String rejectReason;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean isReviewableByModerator() {
        return reviewableByModerator;
    }

    public void setReviewableByModerator(Boolean reviewableByModerator) {
        this.reviewableByModerator = reviewableByModerator;
    }

    public Long getReviewableByGroupId() {
        return reviewableByGroupId;
    }

    public void setReviewableByGroupId(Long reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean isPotentialSpam() {
        return potentialSpam;
    }

    public void setPotentialSpam(Boolean potentialSpam) {
        this.potentialSpam = potentialSpam;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetCreatedById() {
        return targetCreatedById;
    }

    public void setTargetCreatedById(String targetCreatedById) {
        this.targetCreatedById = targetCreatedById;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Instant getLatestScore() {
        return latestScore;
    }

    public void setLatestScore(Instant latestScore) {
        this.latestScore = latestScore;
    }

    public Boolean isForceReview() {
        return forceReview;
    }

    public void setForceReview(Boolean forceReview) {
        this.forceReview = forceReview;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewablesDTO)) {
            return false;
        }

        return id != null && id.equals(((ReviewablesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewablesDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", status=" + getStatus() +
            ", reviewableByModerator='" + isReviewableByModerator() + "'" +
            ", reviewableByGroupId=" + getReviewableByGroupId() +
            ", categoryId=" + getCategoryId() +
            ", topicId=" + getTopicId() +
            ", score=" + getScore() +
            ", potentialSpam='" + isPotentialSpam() + "'" +
            ", targetId=" + getTargetId() +
            ", targetType='" + getTargetType() + "'" +
            ", targetCreatedById='" + getTargetCreatedById() + "'" +
            ", payload='" + getPayload() + "'" +
            ", version=" + getVersion() +
            ", latestScore='" + getLatestScore() + "'" +
            ", forceReview='" + isForceReview() + "'" +
            ", rejectReason='" + getRejectReason() + "'" +
            "}";
    }
}
