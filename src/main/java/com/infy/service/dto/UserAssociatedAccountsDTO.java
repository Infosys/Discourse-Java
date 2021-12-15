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
 * A DTO for the {@link com.infy.domain.UserAssociatedAccounts} entity.
 */
public class UserAssociatedAccountsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String providerName;

    @NotNull
    private String providerUid;

    private String userId;

    @NotNull
    private Instant lastUsed;

    @NotNull
    private String info;

    @NotNull
    private String credentials;

    @NotNull
    private String extra;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderUid() {
        return providerUid;
    }

    public void setProviderUid(String providerUid) {
        this.providerUid = providerUid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAssociatedAccountsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAssociatedAccountsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAssociatedAccountsDTO{" +
            "id=" + getId() +
            ", providerName='" + getProviderName() + "'" +
            ", providerUid='" + getProviderUid() + "'" +
            ", userId='" + getUserId() + "'" +
            ", lastUsed='" + getLastUsed() + "'" +
            ", info='" + getInfo() + "'" +
            ", credentials='" + getCredentials() + "'" +
            ", extra='" + getExtra() + "'" +
            "}";
    }
}
