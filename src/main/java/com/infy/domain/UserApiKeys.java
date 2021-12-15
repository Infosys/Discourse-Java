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
 * A UserApiKeys.
 */
@Entity
@Table(name = "user_api_keys")
public class UserApiKeys extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @NotNull
    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "push_url")
    private String pushUrl;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "scopes")
    private String scopes;

    @NotNull
    @Column(name = "last_used_at", nullable = false)
    private Instant lastUsedAt;

    @NotNull
    @Column(name = "key_hash", nullable = false)
    private String keyHash;

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

    public UserApiKeys userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public UserApiKeys clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public UserApiKeys applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public UserApiKeys pushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
        return this;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public UserApiKeys revokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
        return this;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public String getScopes() {
        return scopes;
    }

    public UserApiKeys scopes(String scopes) {
        this.scopes = scopes;
        return this;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

    public UserApiKeys lastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
        return this;
    }

    public void setLastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public UserApiKeys keyHash(String keyHash) {
        this.keyHash = keyHash;
        return this;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApiKeys)) {
            return false;
        }
        return id != null && id.equals(((UserApiKeys) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserApiKeys{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", applicationName='" + getApplicationName() + "'" +
            ", pushUrl='" + getPushUrl() + "'" +
            ", revokedAt='" + getRevokedAt() + "'" +
            ", scopes='" + getScopes() + "'" +
            ", lastUsedAt='" + getLastUsedAt() + "'" +
            ", keyHash='" + getKeyHash() + "'" +
            "}";
    }
}
