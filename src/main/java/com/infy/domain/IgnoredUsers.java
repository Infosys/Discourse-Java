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
 * A IgnoredUsers.
 */
@Entity
@Table(name = "ignored_users")
public class IgnoredUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "ignored_user_id", nullable = false)
    private String ignoredUserId;

    @Column(name = "summarized_at")
    private Instant summarizedAt;

    @NotNull
    @Column(name = "expiring_at", nullable = false)
    private Instant expiringAt;

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

    public IgnoredUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIgnoredUserId() {
        return ignoredUserId;
    }

    public IgnoredUsers ignoredUserId(String ignoredUserId) {
        this.ignoredUserId = ignoredUserId;
        return this;
    }

    public void setIgnoredUserId(String ignoredUserId) {
        this.ignoredUserId = ignoredUserId;
    }

    public Instant getSummarizedAt() {
        return summarizedAt;
    }

    public IgnoredUsers summarizedAt(Instant summarizedAt) {
        this.summarizedAt = summarizedAt;
        return this;
    }

    public void setSummarizedAt(Instant summarizedAt) {
        this.summarizedAt = summarizedAt;
    }

    public Instant getExpiringAt() {
        return expiringAt;
    }

    public IgnoredUsers expiringAt(Instant expiringAt) {
        this.expiringAt = expiringAt;
        return this;
    }

    public void setExpiringAt(Instant expiringAt) {
        this.expiringAt = expiringAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IgnoredUsers)) {
            return false;
        }
        return id != null && id.equals(((IgnoredUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IgnoredUsers{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", ignoredUserId='" + getIgnoredUserId() + "'" +
            ", summarizedAt='" + getSummarizedAt() + "'" +
            ", expiringAt='" + getExpiringAt() + "'" +
            "}";
    }
}
