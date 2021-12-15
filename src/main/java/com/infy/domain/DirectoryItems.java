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

/**
 * A DirectoryItems.
 */
@Entity
@Table(name = "directory_items")
public class DirectoryItems extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "period_type", nullable = false)
    private Integer periodType;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "likes_received", nullable = false)
    private Integer likesReceived;

    @NotNull
    @Column(name = "likes_given", nullable = false)
    private Integer likesGiven;

    @NotNull
    @Column(name = "topics_entered", nullable = false)
    private Integer topicsEntered;

    @NotNull
    @Column(name = "topic_count", nullable = false)
    private Integer topicCount;

    @NotNull
    @Column(name = "post_count", nullable = false)
    private Integer postCount;

    @NotNull
    @Column(name = "days_visited", nullable = false)
    private Integer daysVisited;

    @NotNull
    @Column(name = "posts_read", nullable = false)
    private Integer postsRead;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public DirectoryItems periodType(Integer periodType) {
        this.periodType = periodType;
        return this;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getUserId() {
        return userId;
    }

    public DirectoryItems userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLikesReceived() {
        return likesReceived;
    }

    public DirectoryItems likesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
        return this;
    }

    public void setLikesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
    }

    public Integer getLikesGiven() {
        return likesGiven;
    }

    public DirectoryItems likesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
        return this;
    }

    public void setLikesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
    }

    public Integer getTopicsEntered() {
        return topicsEntered;
    }

    public DirectoryItems topicsEntered(Integer topicsEntered) {
        this.topicsEntered = topicsEntered;
        return this;
    }

    public void setTopicsEntered(Integer topicsEntered) {
        this.topicsEntered = topicsEntered;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public DirectoryItems topicCount(Integer topicCount) {
        this.topicCount = topicCount;
        return this;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public DirectoryItems postCount(Integer postCount) {
        this.postCount = postCount;
        return this;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getDaysVisited() {
        return daysVisited;
    }

    public DirectoryItems daysVisited(Integer daysVisited) {
        this.daysVisited = daysVisited;
        return this;
    }

    public void setDaysVisited(Integer daysVisited) {
        this.daysVisited = daysVisited;
    }

    public Integer getPostsRead() {
        return postsRead;
    }

    public DirectoryItems postsRead(Integer postsRead) {
        this.postsRead = postsRead;
        return this;
    }

    public void setPostsRead(Integer postsRead) {
        this.postsRead = postsRead;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectoryItems)) {
            return false;
        }
        return id != null && id.equals(((DirectoryItems) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectoryItems{" +
            "id=" + getId() +
            ", periodType=" + getPeriodType() +
            ", userId='" + getUserId() + "'" +
            ", likesReceived=" + getLikesReceived() +
            ", likesGiven=" + getLikesGiven() +
            ", topicsEntered=" + getTopicsEntered() +
            ", topicCount=" + getTopicCount() +
            ", postCount=" + getPostCount() +
            ", daysVisited=" + getDaysVisited() +
            ", postsRead=" + getPostsRead() +
            "}";
    }
}
