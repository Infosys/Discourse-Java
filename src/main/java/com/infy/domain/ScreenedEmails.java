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
 * A ScreenedEmails.
 */
@Entity
@Table(name = "screened_emails")
public class ScreenedEmails extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "action_type", nullable = false)
    private Integer actionType;

    @NotNull
    @Column(name = "match_count", nullable = false)
    private Integer matchCount;

    @Column(name = "last_match_at")
    private Instant lastMatchAt;

    @Column(name = "ip_address")
    private String ipAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public ScreenedEmails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getActionType() {
        return actionType;
    }

    public ScreenedEmails actionType(Integer actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public ScreenedEmails matchCount(Integer matchCount) {
        this.matchCount = matchCount;
        return this;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public Instant getLastMatchAt() {
        return lastMatchAt;
    }

    public ScreenedEmails lastMatchAt(Instant lastMatchAt) {
        this.lastMatchAt = lastMatchAt;
        return this;
    }

    public void setLastMatchAt(Instant lastMatchAt) {
        this.lastMatchAt = lastMatchAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public ScreenedEmails ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreenedEmails)) {
            return false;
        }
        return id != null && id.equals(((ScreenedEmails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScreenedEmails{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", actionType=" + getActionType() +
            ", matchCount=" + getMatchCount() +
            ", lastMatchAt='" + getLastMatchAt() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            "}";
    }
}
