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
 * A GroupUsers.
 */
@Entity
@Table(name = "group_users")
public class GroupUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "owner", nullable = false)
    private Boolean owner;

    @NotNull
    @Column(name = "notification_level", nullable = false)
    private Integer notificationLevel;

    @NotNull
    @Column(name = "first_unread_pm_at", nullable = false)
    private Instant firstUnreadPmAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GroupUsers groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public GroupUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isOwner() {
        return owner;
    }

    public GroupUsers owner(Boolean owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public GroupUsers notificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
        return this;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public Instant getFirstUnreadPmAt() {
        return firstUnreadPmAt;
    }

    public GroupUsers firstUnreadPmAt(Instant firstUnreadPmAt) {
        this.firstUnreadPmAt = firstUnreadPmAt;
        return this;
    }

    public void setFirstUnreadPmAt(Instant firstUnreadPmAt) {
        this.firstUnreadPmAt = firstUnreadPmAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupUsers)) {
            return false;
        }
        return id != null && id.equals(((GroupUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupUsers{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", userId='" + getUserId() + "'" +
            ", owner='" + isOwner() + "'" +
            ", notificationLevel=" + getNotificationLevel() +
            ", firstUnreadPmAt='" + getFirstUnreadPmAt() + "'" +
            "}";
    }
}
