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
 * A AllowedPmUsers.
 */
@Entity
@Table(name = "allowed_pm_users")
public class AllowedPmUsers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "allowed_pm_user_id", nullable = false)
    private String allowedPmUserId;

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

    public AllowedPmUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllowedPmUserId() {
        return allowedPmUserId;
    }

    public AllowedPmUsers allowedPmUserId(String allowedPmUserId) {
        this.allowedPmUserId = allowedPmUserId;
        return this;
    }

    public void setAllowedPmUserId(String allowedPmUserId) {
        this.allowedPmUserId = allowedPmUserId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AllowedPmUsers)) {
            return false;
        }
        return id != null && id.equals(((AllowedPmUsers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AllowedPmUsers{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", allowedPmUserId='" + getAllowedPmUserId() + "'" +
            "}";
    }
}
