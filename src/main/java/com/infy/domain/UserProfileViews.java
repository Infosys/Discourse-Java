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
 * A UserProfileViews.
 */
@Entity
@Table(name = "user_profile_views")
public class UserProfileViews extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_profile_id", nullable = false)
    private Long userProfileId;

    @NotNull
    @Column(name = "viewed_at", nullable = false)
    private Instant viewedAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_id")
    private String userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public UserProfileViews userProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
        return this;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Instant getViewedAt() {
        return viewedAt;
    }

    public UserProfileViews viewedAt(Instant viewedAt) {
        this.viewedAt = viewedAt;
        return this;
    }

    public void setViewedAt(Instant viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public UserProfileViews ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserId() {
        return userId;
    }

    public UserProfileViews userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfileViews)) {
            return false;
        }
        return id != null && id.equals(((UserProfileViews) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfileViews{" +
            "id=" + getId() +
            ", userProfileId=" + getUserProfileId() +
            ", viewedAt='" + getViewedAt() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
