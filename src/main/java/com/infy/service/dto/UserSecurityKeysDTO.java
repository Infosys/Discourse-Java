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
 * A DTO for the {@link com.infy.domain.UserSecurityKeys} entity.
 */
public class UserSecurityKeysDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String credentialId;

    @NotNull
    private String publicKey;

    @NotNull
    private Integer factorType;

    @NotNull
    private Boolean enabled;

    @NotNull
    private String name;

    private Instant lastUsed;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Integer getFactorType() {
        return factorType;
    }

    public void setFactorType(Integer factorType) {
        this.factorType = factorType;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSecurityKeysDTO)) {
            return false;
        }

        return id != null && id.equals(((UserSecurityKeysDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSecurityKeysDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", credentialId='" + getCredentialId() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", factorType=" + getFactorType() +
            ", enabled='" + isEnabled() + "'" +
            ", name='" + getName() + "'" +
            ", lastUsed='" + getLastUsed() + "'" +
            "}";
    }
}
