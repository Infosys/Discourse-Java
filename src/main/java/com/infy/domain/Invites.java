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
 * A Invites.
 */
@Entity
@Table(name = "invites")
public class Invites extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "invite_key", nullable = false)
    private String inviteKey;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "invited_by_id", nullable = false)
    private String invitedById;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "redeemed_at")
    private Instant redeemedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "invalidated_at")
    private Instant invalidatedAt;

    @NotNull
    @Column(name = "moderator", nullable = false)
    private Boolean moderator;

    @Column(name = "custom_message")
    private String customMessage;

    @Column(name = "emailed_status")
    private Integer emailedStatus;

    @NotNull
    @Column(name = "max_redemptions_allowed", nullable = false)
    private Integer maxRedemptionsAllowed;

    @NotNull
    @Column(name = "redemption_count", nullable = false)
    private Integer redemptionCount;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "email_token")
    private String emailToken;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviteKey() {
        return inviteKey;
    }

    public Invites inviteKey(String inviteKey) {
        this.inviteKey = inviteKey;
        return this;
    }

    public void setInviteKey(String inviteKey) {
        this.inviteKey = inviteKey;
    }

    public String getEmail() {
        return email;
    }

    public Invites email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvitedById() {
        return invitedById;
    }

    public Invites invitedById(String invitedById) {
        this.invitedById = invitedById;
        return this;
    }

    public void setInvitedById(String invitedById) {
        this.invitedById = invitedById;
    }

    public String getUserId() {
        return userId;
    }

    public Invites userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getRedeemedAt() {
        return redeemedAt;
    }

    public Invites redeemedAt(Instant redeemedAt) {
        this.redeemedAt = redeemedAt;
        return this;
    }

    public void setRedeemedAt(Instant redeemedAt) {
        this.redeemedAt = redeemedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Invites deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public Invites deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Instant getInvalidatedAt() {
        return invalidatedAt;
    }

    public Invites invalidatedAt(Instant invalidatedAt) {
        this.invalidatedAt = invalidatedAt;
        return this;
    }

    public void setInvalidatedAt(Instant invalidatedAt) {
        this.invalidatedAt = invalidatedAt;
    }

    public Boolean isModerator() {
        return moderator;
    }

    public Invites moderator(Boolean moderator) {
        this.moderator = moderator;
        return this;
    }

    public void setModerator(Boolean moderator) {
        this.moderator = moderator;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public Invites customMessage(String customMessage) {
        this.customMessage = customMessage;
        return this;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Integer getEmailedStatus() {
        return emailedStatus;
    }

    public Invites emailedStatus(Integer emailedStatus) {
        this.emailedStatus = emailedStatus;
        return this;
    }

    public void setEmailedStatus(Integer emailedStatus) {
        this.emailedStatus = emailedStatus;
    }

    public Integer getMaxRedemptionsAllowed() {
        return maxRedemptionsAllowed;
    }

    public Invites maxRedemptionsAllowed(Integer maxRedemptionsAllowed) {
        this.maxRedemptionsAllowed = maxRedemptionsAllowed;
        return this;
    }

    public void setMaxRedemptionsAllowed(Integer maxRedemptionsAllowed) {
        this.maxRedemptionsAllowed = maxRedemptionsAllowed;
    }

    public Integer getRedemptionCount() {
        return redemptionCount;
    }

    public Invites redemptionCount(Integer redemptionCount) {
        this.redemptionCount = redemptionCount;
        return this;
    }

    public void setRedemptionCount(Integer redemptionCount) {
        this.redemptionCount = redemptionCount;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Invites expiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public Invites emailToken(String emailToken) {
        this.emailToken = emailToken;
        return this;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invites)) {
            return false;
        }
        return id != null && id.equals(((Invites) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invites{" +
            "id=" + getId() +
            ", inviteKey='" + getInviteKey() + "'" +
            ", email='" + getEmail() + "'" +
            ", invitedById='" + getInvitedById() + "'" +
            ", userId='" + getUserId() + "'" +
            ", redeemedAt='" + getRedeemedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedById='" + getDeletedById() + "'" +
            ", invalidatedAt='" + getInvalidatedAt() + "'" +
            ", moderator='" + isModerator() + "'" +
            ", customMessage='" + getCustomMessage() + "'" +
            ", emailedStatus=" + getEmailedStatus() +
            ", maxRedemptionsAllowed=" + getMaxRedemptionsAllowed() +
            ", redemptionCount=" + getRedemptionCount() +
            ", expiresAt='" + getExpiresAt() + "'" +
            ", emailToken='" + getEmailToken() + "'" +
            "}";
    }
}
