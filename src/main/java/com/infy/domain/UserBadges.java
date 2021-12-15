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
 * A UserBadges.
 */
@Entity
@Table(name = "user_badges")
public class UserBadges extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "granted_at", nullable = false)
    private Instant grantedAt;

    @NotNull
    @Column(name = "granted_by_id", nullable = false)
    private String grantedById;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "notification_id")
    private Long notificationId;

    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column(name = "featured_rank")
    private Integer featuredRank;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public UserBadges badgeId(Long badgeId) {
        this.badgeId = badgeId;
        return this;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getUserId() {
        return userId;
    }

    public UserBadges userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getGrantedAt() {
        return grantedAt;
    }

    public UserBadges grantedAt(Instant grantedAt) {
        this.grantedAt = grantedAt;
        return this;
    }

    public void setGrantedAt(Instant grantedAt) {
        this.grantedAt = grantedAt;
    }

    public String getGrantedById() {
        return grantedById;
    }

    public UserBadges grantedById(String grantedById) {
        this.grantedById = grantedById;
        return this;
    }

    public void setGrantedById(String grantedById) {
        this.grantedById = grantedById;
    }

    public Long getPostId() {
        return postId;
    }

    public UserBadges postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public UserBadges notificationId(Long notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getSeq() {
        return seq;
    }

    public UserBadges seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getFeaturedRank() {
        return featuredRank;
    }

    public UserBadges featuredRank(Integer featuredRank) {
        this.featuredRank = featuredRank;
        return this;
    }

    public void setFeaturedRank(Integer featuredRank) {
        this.featuredRank = featuredRank;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBadges)) {
            return false;
        }
        return id != null && id.equals(((UserBadges) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserBadges{" +
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
