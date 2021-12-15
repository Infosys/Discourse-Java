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
 * A UserSecondFactors.
 */
@Entity
@Table(name = "user_second_factors")
public class UserSecondFactors extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "method", nullable = false)
    private Integer method;

    @NotNull
    @Column(name = "data", nullable = false)
    private String data;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "last_used")
    private Instant lastUsed;

    @Column(name = "name")
    private String name;

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

    public UserSecondFactors userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMethod() {
        return method;
    }

    public UserSecondFactors method(Integer method) {
        this.method = method;
        return this;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public UserSecondFactors data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public UserSecondFactors enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public UserSecondFactors lastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public String getName() {
        return name;
    }

    public UserSecondFactors name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSecondFactors)) {
            return false;
        }
        return id != null && id.equals(((UserSecondFactors) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSecondFactors{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", method=" + getMethod() +
            ", data='" + getData() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", lastUsed='" + getLastUsed() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
