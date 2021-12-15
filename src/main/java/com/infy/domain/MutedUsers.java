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

/**
 * A MutedUsers.
 */
@Entity
@Table(name = "muted_users")
public class MutedUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "muted_user_id", nullable = false)
    private String mutedUserId;

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

    public MutedUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMutedUserId() {
        return mutedUserId;
    }

    public MutedUsers mutedUserId(String mutedUserId) {
        this.mutedUserId = mutedUserId;
        return this;
    }

    public void setMutedUserId(String mutedUserId) {
        this.mutedUserId = mutedUserId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MutedUsers)) {
            return false;
        }
        return id != null && id.equals(((MutedUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MutedUsers{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", mutedUserId='" + getMutedUserId() + "'" +
            "}";
    }
}
