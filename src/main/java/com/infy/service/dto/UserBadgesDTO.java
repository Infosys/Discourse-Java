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
 * A DTO for the {@link com.infy.domain.UserBadges} entity.
 */
public class UserBadgesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long badgeId;

    @NotNull
    private String userId;

    @NotNull
    private Instant grantedAt;

    @NotNull
    private String grantedById;

    private Long postId;

    private Long notificationId;

    @NotNull
    private Integer seq;

    private Integer featuredRank;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(Instant grantedAt) {
        this.grantedAt = grantedAt;
    }

    public String getGrantedById() {
        return grantedById;
    }

    public void setGrantedById(String grantedById) {
        this.grantedById = grantedById;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getFeaturedRank() {
        return featuredRank;
    }

    public void setFeaturedRank(Integer featuredRank) {
        this.featuredRank = featuredRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBadgesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserBadgesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserBadgesDTO{" +
            "id=" + getId() +
            ", badgeId=" + getBadgeId() +
            ", userId='" + getUserId() + "'" +
            ", grantedAt='" + getGrantedAt() + "'" +
            ", grantedById='" + getGrantedById() + "'" +
            ", postId=" + getPostId() +
            ", notificationId=" + getNotificationId() +
            ", seq=" + getSeq() +
            ", featuredRank=" + getFeaturedRank() +
            "}";
    }
}
