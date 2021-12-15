/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.DirectoryItems} entity.
 */
public class DirectoryItemsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer periodType;

    @NotNull
    private String userId;

    @NotNull
    private Integer likesReceived;

    @NotNull
    private Integer likesGiven;

    @NotNull
    private Integer topicsEntered;

    @NotNull
    private Integer topicCount;

    @NotNull
    private Integer postCount;

    @NotNull
    private Integer daysVisited;

    @NotNull
    private Integer postsRead;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLikesReceived() {
        return likesReceived;
    }

    public void setLikesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
    }

    public Integer getLikesGiven() {
        return likesGiven;
    }

    public void setLikesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
    }

    public Integer getTopicsEntered() {
        return topicsEntered;
    }

    public void setTopicsEntered(Integer topicsEntered) {
        this.topicsEntered = topicsEntered;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getDaysVisited() {
        return daysVisited;
    }

    public void setDaysVisited(Integer daysVisited) {
        this.daysVisited = daysVisited;
    }

    public Integer getPostsRead() {
        return postsRead;
    }

    public void setPostsRead(Integer postsRead) {
        this.postsRead = postsRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectoryItemsDTO)) {
            return false;
        }

        return id != null && id.equals(((DirectoryItemsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectoryItemsDTO{" +
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
