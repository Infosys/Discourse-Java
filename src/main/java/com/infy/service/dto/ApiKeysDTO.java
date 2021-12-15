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
 * A DTO for the {@link com.infy.domain.ApiKeys} entity.
 */
public class ApiKeysDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String userId;

    private String allowedIps;

    @NotNull
    private Boolean hidden;

    private Instant lastUsedAt;

    private Instant revokedAt;

    private String description;

    @NotNull
    private String keyHash;

    @NotNull
    private String truncatedKey;


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

    public String getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    public String getTruncatedKey() {
        return truncatedKey;
    }

    public void setTruncatedKey(String truncatedKey) {
        this.truncatedKey = truncatedKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKeysDTO)) {
            return false;
        }

        return id != null && id.equals(((ApiKeysDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiKeysDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", allowedIps='" + getAllowedIps() + "'" +
            ", hidden='" + isHidden() + "'" +
            ", lastUsedAt='" + getLastUsedAt() + "'" +
            ", revokedAt='" + getRevokedAt() + "'" +
            ", description='" + getDescription() + "'" +
            ", keyHash='" + getKeyHash() + "'" +
            ", truncatedKey='" + getTruncatedKey() + "'" +
            "}";
    }
}
