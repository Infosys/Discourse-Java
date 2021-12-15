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
 * A DTO for the {@link com.infy.domain.EmailChangeRequests} entity.
 */
public class EmailChangeRequestsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    private String oldEmail;

    @NotNull
    private String newEmail;

    private Integer oldEmailTokenId;

    private Integer newEmailTokenId;

    @NotNull
    private Integer changeState;

    private String requestedByUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public Integer getOldEmailTokenId() {
        return oldEmailTokenId;
    }

    public void setOldEmailTokenId(Integer oldEmailTokenId) {
        this.oldEmailTokenId = oldEmailTokenId;
    }

    public Integer getNewEmailTokenId() {
        return newEmailTokenId;
    }

    public void setNewEmailTokenId(Integer newEmailTokenId) {
        this.newEmailTokenId = newEmailTokenId;
    }

    public Integer getChangeState() {
        return changeState;
    }

    public void setChangeState(Integer changeState) {
        this.changeState = changeState;
    }

    public String getRequestedByUserId() {
        return requestedByUserId;
    }

    public void setRequestedByUserId(String requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailChangeRequestsDTO)) {
            return false;
        }

        return id != null && id.equals(((EmailChangeRequestsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailChangeRequestsDTO{" +
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
