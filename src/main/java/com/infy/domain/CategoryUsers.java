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
 * A CategoryUsers.
 */
@Entity
@Table(name = "category_users")
public class CategoryUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "notification_level")
    private Integer notificationLevel;

    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public CategoryUsers categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public CategoryUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public CategoryUsers notificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
        return this;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public CategoryUsers lastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
        return this;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryUsers)) {
            return false;
        }
        return id != null && id.equals(((CategoryUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryUsers{" +
            "id=" + getId() +
            ", categoryId=" + getCategoryId() +
            ", userId='" + getUserId() + "'" +
            ", notificationLevel=" + getNotificationLevel() +
            ", lastSeenAt='" + getLastSeenAt() + "'" +
            "}";
    }
}
