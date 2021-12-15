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
 * A DTO for the {@link com.infy.domain.SingleSignOnRecords} entity.
 */
public class SingleSignOnRecordsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String externalId;

    @NotNull
    private String lastPayload;

    private String externalUsername;

    private String externalEmail;

    private String externalName;

    private String externalAvatarUrl;

    private String externalProfileBackgroundUrl;

    private String externalCardBackgroundUrl;


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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getLastPayload() {
        return lastPayload;
    }

    public void setLastPayload(String lastPayload) {
        this.lastPayload = lastPayload;
    }

    public String getExternalUsername() {
        return externalUsername;
    }

    public void setExternalUsername(String externalUsername) {
        this.externalUsername = externalUsername;
    }

    public String getExternalEmail() {
        return externalEmail;
    }

    public void setExternalEmail(String externalEmail) {
        this.externalEmail = externalEmail;
    }

    public String getExternalName() {
        return externalName;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public String getExternalAvatarUrl() {
        return externalAvatarUrl;
    }

    public void setExternalAvatarUrl(String externalAvatarUrl) {
        this.externalAvatarUrl = externalAvatarUrl;
    }

    public String getExternalProfileBackgroundUrl() {
        return externalProfileBackgroundUrl;
    }

    public void setExternalProfileBackgroundUrl(String externalProfileBackgroundUrl) {
        this.externalProfileBackgroundUrl = externalProfileBackgroundUrl;
    }

    public String getExternalCardBackgroundUrl() {
        return externalCardBackgroundUrl;
    }

    public void setExternalCardBackgroundUrl(String externalCardBackgroundUrl) {
        this.externalCardBackgroundUrl = externalCardBackgroundUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SingleSignOnRecordsDTO)) {
            return false;
        }

        return id != null && id.equals(((SingleSignOnRecordsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SingleSignOnRecordsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", lastPayload='" + getLastPayload() + "'" +
            ", externalUsername='" + getExternalUsername() + "'" +
            ", externalEmail='" + getExternalEmail() + "'" +
            ", externalName='" + getExternalName() + "'" +
            ", externalAvatarUrl='" + getExternalAvatarUrl() + "'" +
            ", externalProfileBackgroundUrl='" + getExternalProfileBackgroundUrl() + "'" +
            ", externalCardBackgroundUrl='" + getExternalCardBackgroundUrl() + "'" +
            "}";
    }
}
