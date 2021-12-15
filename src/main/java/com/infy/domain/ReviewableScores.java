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
 * A ReviewableScores.
 */
@Entity
@Table(name = "reviewable_scores")
public class ReviewableScores extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reviewable_id", nullable = false)
    private Long reviewableId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "reviewable_score_type", nullable = false)
    private Integer reviewableScoreType;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "score", nullable = false)
    private Double score;

    @NotNull
    @Column(name = "take_action_bonus", nullable = false)
    private Double takeActionBonus;

    @Column(name = "reviewed_by_id")
    private String reviewedById;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @Column(name = "meta_topic_id")
    private Long metaTopicId;

    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "user_accuracy_bonus", nullable = false)
    private Double userAccuracyBonus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewableId() {
        return reviewableId;
    }

    public ReviewableScores reviewableId(Long reviewableId) {
        this.reviewableId = reviewableId;
        return this;
    }

    public void setReviewableId(Long reviewableId) {
        this.reviewableId = reviewableId;
    }

    public String getUserId() {
        return userId;
    }

    public ReviewableScores userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getReviewableScoreType() {
        return reviewableScoreType;
    }

    public ReviewableScores reviewableScoreType(Integer reviewableScoreType) {
        this.reviewableScoreType = reviewableScoreType;
        return this;
    }

    public void setReviewableScoreType(Integer reviewableScoreType) {
        this.reviewableScoreType = reviewableScoreType;
    }

    public Integer getStatus() {
        return status;
    }

    public ReviewableScores status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public ReviewableScores score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getTakeActionBonus() {
        return takeActionBonus;
    }

    public ReviewableScores takeActionBonus(Double takeActionBonus) {
        this.takeActionBonus = takeActionBonus;
        return this;
    }

    public void setTakeActionBonus(Double takeActionBonus) {
        this.takeActionBonus = takeActionBonus;
    }

    public String getReviewedById() {
        return reviewedById;
    }

    public ReviewableScores reviewedById(String reviewedById) {
        this.reviewedById = reviewedById;
        return this;
    }

    public void setReviewedById(String reviewedById) {
        this.reviewedById = reviewedById;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public ReviewableScores reviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
        return this;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public Long getMetaTopicId() {
        return metaTopicId;
    }

    public ReviewableScores metaTopicId(Long metaTopicId) {
        this.metaTopicId = metaTopicId;
        return this;
    }

    public void setMetaTopicId(Long metaTopicId) {
        this.metaTopicId = metaTopicId;
    }

    public String getReason() {
        return reason;
    }

    public ReviewableScores reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getUserAccuracyBonus() {
        return userAccuracyBonus;
    }

    public ReviewableScores userAccuracyBonus(Double userAccuracyBonus) {
        this.userAccuracyBonus = userAccuracyBonus;
        return this;
    }

    public void setUserAccuracyBonus(Double userAccuracyBonus) {
        this.userAccuracyBonus = userAccuracyBonus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewableScores)) {
            return false;
        }
        return id != null && id.equals(((ReviewableScores) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewableScores{" +
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
