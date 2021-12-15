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
 * A EmailChangeRequests.
 */
@Entity
@Table(name = "email_change_requests")
public class EmailChangeRequests extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "old_email")
    private String oldEmail;

    @NotNull
    @Column(name = "new_email", nullable = false)
    private String newEmail;

    @Column(name = "old_email_token_id")
    private Integer oldEmailTokenId;

    @Column(name = "new_email_token_id")
    private Integer newEmailTokenId;

    @NotNull
    @Column(name = "change_state", nullable = false)
    private Integer changeState;

    @Column(name = "requested_by_user_id")
    private String requestedByUserId;

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

    public EmailChangeRequests userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public EmailChangeRequests oldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
        return this;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public EmailChangeRequests newEmail(String newEmail) {
        this.newEmail = newEmail;
        return this;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public Integer getOldEmailTokenId() {
        return oldEmailTokenId;
    }

    public EmailChangeRequests oldEmailTokenId(Integer oldEmailTokenId) {
        this.oldEmailTokenId = oldEmailTokenId;
        return this;
    }

    public void setOldEmailTokenId(Integer oldEmailTokenId) {
        this.oldEmailTokenId = oldEmailTokenId;
    }

    public Integer getNewEmailTokenId() {
        return newEmailTokenId;
    }

    public EmailChangeRequests newEmailTokenId(Integer newEmailTokenId) {
        this.newEmailTokenId = newEmailTokenId;
        return this;
    }

    public void setNewEmailTokenId(Integer newEmailTokenId) {
        this.newEmailTokenId = newEmailTokenId;
    }

    public Integer getChangeState() {
        return changeState;
    }

    public EmailChangeRequests changeState(Integer changeState) {
        this.changeState = changeState;
        return this;
    }

    public void setChangeState(Integer changeState) {
        this.changeState = changeState;
    }

    public String getRequestedByUserId() {
        return requestedByUserId;
    }

    public EmailChangeRequests requestedByUserId(String requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
        return this;
    }

    public void setRequestedByUserId(String requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailChangeRequests)) {
            return false;
        }
        return id != null && id.equals(((EmailChangeRequests) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailChangeRequests{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", oldEmail='" + getOldEmail() + "'" +
            ", newEmail='" + getNewEmail() + "'" +
            ", oldEmailTokenId=" + getOldEmailTokenId() +
            ", newEmailTokenId=" + getNewEmailTokenId() +
            ", changeState=" + getChangeState() +
            ", requestedByUserId='" + getRequestedByUserId() + "'" +
            "}";
    }
}
