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
 * A ApiKeys.
 */
@Entity
@Table(name = "api_keys")
public class ApiKeys extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "allowed_ips")
    private String allowedIps;

    @NotNull
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;

    @Column(name = "last_used_at")
    private Instant lastUsedAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "key_hash", nullable = false)
    private String keyHash;

    @NotNull
    @Column(name = "truncated_key", nullable = false)
    private String truncatedKey;

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

    public ApiKeys userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllowedIps() {
        return allowedIps;
    }

    public ApiKeys allowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
        return this;
    }

    public void setAllowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public ApiKeys hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

    public ApiKeys lastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
        return this;
    }

    public void setLastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public ApiKeys revokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
        return this;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public String getDescription() {
        return description;
    }

    public ApiKeys description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public ApiKeys keyHash(String keyHash) {
        this.keyHash = keyHash;
        return this;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    public String getTruncatedKey() {
        return truncatedKey;
    }

    public ApiKeys truncatedKey(String truncatedKey) {
        this.truncatedKey = truncatedKey;
        return this;
    }

    public void setTruncatedKey(String truncatedKey) {
        this.truncatedKey = truncatedKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKeys)) {
            return false;
        }
        return id != null && id.equals(((ApiKeys) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiKeys{" +
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
