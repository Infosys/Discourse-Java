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
 * A UserAssociatedAccounts.
 */
@Entity
@Table(name = "user_associated_accounts")
public class UserAssociatedAccounts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @NotNull
    @Column(name = "provider_uid", nullable = false)
    private String providerUid;

    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "last_used", nullable = false)
    private Instant lastUsed;

    @NotNull
    @Column(name = "info", nullable = false)
    private String info;

    @NotNull
    @Column(name = "credentials", nullable = false)
    private String credentials;

    @NotNull
    @Column(name = "extra", nullable = false)
    private String extra;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public UserAssociatedAccounts providerName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderUid() {
        return providerUid;
    }

    public UserAssociatedAccounts providerUid(String providerUid) {
        this.providerUid = providerUid;
        return this;
    }

    public void setProviderUid(String providerUid) {
        this.providerUid = providerUid;
    }

    public String getUserId() {
        return userId;
    }

    public UserAssociatedAccounts userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public UserAssociatedAccounts lastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public String getInfo() {
        return info;
    }

    public UserAssociatedAccounts info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCredentials() {
        return credentials;
    }

    public UserAssociatedAccounts credentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getExtra() {
        return extra;
    }

    public UserAssociatedAccounts extra(String extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAssociatedAccounts)) {
            return false;
        }
        return id != null && id.equals(((UserAssociatedAccounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAssociatedAccounts{" +
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
