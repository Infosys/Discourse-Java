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
import java.util.HashSet;
import java.util.Set;

/**
 * A UserSecurityKeys.
 */
@Entity
@Table(name = "user_security_keys")
public class UserSecurityKeys extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "credential_id", nullable = false)
    private String credentialId;

    @NotNull
    @Column(name = "public_key", nullable = false)
    private String publicKey;

    @NotNull
    @Column(name = "factor_type", nullable = false)
    private Integer factorType;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_used")
    private Instant lastUsed;

    @OneToMany(mappedBy = "userSecurityKeys")
    private Set<Users> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public UserSecurityKeys userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public UserSecurityKeys credentialId(String credentialId) {
        this.credentialId = credentialId;
        return this;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public UserSecurityKeys publicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Integer getFactorType() {
        return factorType;
    }

    public UserSecurityKeys factorType(Integer factorType) {
        this.factorType = factorType;
        return this;
    }

    public void setFactorType(Integer factorType) {
        this.factorType = factorType;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public UserSecurityKeys enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public UserSecurityKeys name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public UserSecurityKeys lastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public UserSecurityKeys users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public UserSecurityKeys addUsers(Users users) {
        this.users.add(users);
        users.setUserSecurityKeys(this);
        return this;
    }

    public UserSecurityKeys removeUsers(Users users) {
        this.users.remove(users);
        users.setUserSecurityKeys(null);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSecurityKeys)) {
            return false;
        }
        return id != null && id.equals(((UserSecurityKeys) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSecurityKeys{" +
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
