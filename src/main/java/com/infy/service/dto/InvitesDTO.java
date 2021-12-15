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
 * A DTO for the {@link com.infy.domain.Invites} entity.
 */
public class InvitesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String inviteKey;

    private String email;

    @NotNull
    private String invitedById;

    private String userId;

    private Instant redeemedAt;

    private Instant deletedAt;

    private String deletedById;

    private Instant invalidatedAt;

    @NotNull
    private Boolean moderator;

    private String customMessage;

    private Integer emailedStatus;

    @NotNull
    private Integer maxRedemptionsAllowed;

    @NotNull
    private Integer redemptionCount;

    @NotNull
    private Instant expiresAt;

    private String emailToken;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviteKey() {
        return inviteKey;
    }

    public void setInviteKey(String inviteKey) {
        this.inviteKey = inviteKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvitedById() {
        return invitedById;
    }

    public void setInvitedById(String invitedById) {
        this.invitedById = invitedById;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getRedeemedAt() {
        return redeemedAt;
    }

    public void setRedeemedAt(Instant redeemedAt) {
        this.redeemedAt = redeemedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Instant getInvalidatedAt() {
        return invalidatedAt;
    }

    public void setInvalidatedAt(Instant invalidatedAt) {
        this.invalidatedAt = invalidatedAt;
    }

    public Boolean isModerator() {
        return moderator;
    }

    public void setModerator(Boolean moderator) {
        this.moderator = moderator;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Integer getEmailedStatus() {
        return emailedStatus;
    }

    public void setEmailedStatus(Integer emailedStatus) {
        this.emailedStatus = emailedStatus;
    }

    public Integer getMaxRedemptionsAllowed() {
        return maxRedemptionsAllowed;
    }

    public void setMaxRedemptionsAllowed(Integer maxRedemptionsAllowed) {
        this.maxRedemptionsAllowed = maxRedemptionsAllowed;
    }

    public Integer getRedemptionCount() {
        return redemptionCount;
    }

    public void setRedemptionCount(Integer redemptionCount) {
        this.redemptionCount = redemptionCount;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitesDTO)) {
            return false;
        }

        return id != null && id.equals(((InvitesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvitesDTO{" +
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
