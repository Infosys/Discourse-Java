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
 * A Reviewables.
 */
@Entity
@Table(name = "reviewables")
public class Reviewables extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "reviewable_by_moderator", nullable = false)
    private Boolean reviewableByModerator;

    @Column(name = "reviewable_by_group_id")
    private Long reviewableByGroupId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "topic_id")
    private Long topicId;

    @NotNull
    @Column(name = "score", nullable = false)
    private Double score;

    @NotNull
    @Column(name = "potential_spam", nullable = false)
    private Boolean potentialSpam;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "target_type")
    private String targetType;

    @Column(name = "target_created_by_id")
    private String targetCreatedById;

    @Column(name = "payload")
    private String payload;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "latest_score")
    private Instant latestScore;

    @NotNull
    @Column(name = "force_review", nullable = false)
    private Boolean forceReview;

    @Column(name = "reject_reason")
    private String rejectReason;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Reviewables type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public Reviewables status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean isReviewableByModerator() {
        return reviewableByModerator;
    }

    public Reviewables reviewableByModerator(Boolean reviewableByModerator) {
        this.reviewableByModerator = reviewableByModerator;
        return this;
    }

    public void setReviewableByModerator(Boolean reviewableByModerator) {
        this.reviewableByModerator = reviewableByModerator;
    }

    public Long getReviewableByGroupId() {
        return reviewableByGroupId;
    }

    public Reviewables reviewableByGroupId(Long reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
        return this;
    }

    public void setReviewableByGroupId(Long reviewableByGroupId) {
        this.reviewableByGroupId = reviewableByGroupId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Reviewables categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Reviewables topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Double getScore() {
        return score;
    }

    public Reviewables score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean isPotentialSpam() {
        return potentialSpam;
    }

    public Reviewables potentialSpam(Boolean potentialSpam) {
        this.potentialSpam = potentialSpam;
        return this;
    }

    public void setPotentialSpam(Boolean potentialSpam) {
        this.potentialSpam = potentialSpam;
    }

    public Long getTargetId() {
        return targetId;
    }

    public Reviewables targetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public Reviewables targetType(String targetType) {
        this.targetType = targetType;
        return this;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetCreatedById() {
        return targetCreatedById;
    }

    public Reviewables targetCreatedById(String targetCreatedById) {
        this.targetCreatedById = targetCreatedById;
        return this;
    }

    public void setTargetCreatedById(String targetCreatedById) {
        this.targetCreatedById = targetCreatedById;
    }

    public String getPayload() {
        return payload;
    }

    public Reviewables payload(String payload) {
        this.payload = payload;
        return this;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getVersion() {
        return version;
    }

    public Reviewables version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Instant getLatestScore() {
        return latestScore;
    }

    public Reviewables latestScore(Instant latestScore) {
        this.latestScore = latestScore;
        return this;
    }

    public void setLatestScore(Instant latestScore) {
        this.latestScore = latestScore;
    }

    public Boolean isForceReview() {
        return forceReview;
    }

    public Reviewables forceReview(Boolean forceReview) {
        this.forceReview = forceReview;
        return this;
    }

    public void setForceReview(Boolean forceReview) {
        this.forceReview = forceReview;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public Reviewables rejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
        return this;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reviewables)) {
            return false;
        }
        return id != null && id.equals(((Reviewables) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reviewables{" +
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
