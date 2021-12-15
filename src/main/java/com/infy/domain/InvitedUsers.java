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
 * A InvitedUsers.
 */
@Entity
@Table(name = "invited_users")
public class InvitedUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "invite_id", nullable = false)
    private Long inviteId;

    @Column(name = "redeemed_at")
    private Instant redeemedAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public InvitedUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getInviteId() {
        return inviteId;
    }

    public InvitedUsers inviteId(Long inviteId) {
        this.inviteId = inviteId;
        return this;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }

    public Instant getRedeemedAt() {
        return redeemedAt;
    }

    public InvitedUsers redeemedAt(Instant redeemedAt) {
        this.redeemedAt = redeemedAt;
        return this;
    }

    public void setRedeemedAt(Instant redeemedAt) {
        this.redeemedAt = redeemedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public InvitedUsers updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitedUsers)) {
            return false;
        }
        return id != null && id.equals(((InvitedUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvitedUsers{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", inviteId=" + getInviteId() +
            ", redeemedAt='" + getRedeemedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
