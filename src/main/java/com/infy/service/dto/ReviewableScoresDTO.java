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
 * A DTO for the {@link com.infy.domain.ReviewableScores} entity.
 */
public class ReviewableScoresDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long reviewableId;

    @NotNull
    private String userId;

    @NotNull
    private Integer reviewableScoreType;

    @NotNull
    private Integer status;

    @NotNull
    private Double score;

    @NotNull
    private Double takeActionBonus;

    private String reviewedById;

    private Instant reviewedAt;

    private Long metaTopicId;

    private String reason;

    @NotNull
    private Double userAccuracyBonus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewableId() {
        return reviewableId;
    }

    public void setReviewableId(Long reviewableId) {
        this.reviewableId = reviewableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getReviewableScoreType() {
        return reviewableScoreType;
    }

    public void setReviewableScoreType(Integer reviewableScoreType) {
        this.reviewableScoreType = reviewableScoreType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getTakeActionBonus() {
        return takeActionBonus;
    }

    public void setTakeActionBonus(Double takeActionBonus) {
        this.takeActionBonus = takeActionBonus;
    }

    public String getReviewedById() {
        return reviewedById;
    }

    public void setReviewedById(String reviewedById) {
        this.reviewedById = reviewedById;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public Long getMetaTopicId() {
        return metaTopicId;
    }

    public void setMetaTopicId(Long metaTopicId) {
        this.metaTopicId = metaTopicId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getUserAccuracyBonus() {
        return userAccuracyBonus;
    }

    public void setUserAccuracyBonus(Double userAccuracyBonus) {
        this.userAccuracyBonus = userAccuracyBonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewableScoresDTO)) {
            return false;
        }

        return id != null && id.equals(((ReviewableScoresDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewableScoresDTO{" +
            "id=" + getId() +
            ", reviewableId=" + getReviewableId() +
            ", userId='" + getUserId() + "'" +
            ", reviewableScoreType=" + getReviewableScoreType() +
            ", status=" + getStatus() +
            ", score=" + getScore() +
            ", takeActionBonus=" + getTakeActionBonus() +
            ", reviewedById='" + getReviewedById() + "'" +
            ", reviewedAt='" + getReviewedAt() + "'" +
            ", metaTopicId=" + getMetaTopicId() +
            ", reason='" + getReason() + "'" +
            ", userAccuracyBonus=" + getUserAccuracyBonus() +
            "}";
    }
}
