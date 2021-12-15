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
 * A UserApiKeyScopes.
 */
@Entity
@Table(name = "user_api_key_scopes")
public class UserApiKeyScopes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_api_key_id", nullable = false)
    private Long userApiKeyId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "allowed_parameters")
    private String allowedParameters;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserApiKeyId() {
        return userApiKeyId;
    }

    public UserApiKeyScopes userApiKeyId(Long userApiKeyId) {
        this.userApiKeyId = userApiKeyId;
        return this;
    }

    public void setUserApiKeyId(Long userApiKeyId) {
        this.userApiKeyId = userApiKeyId;
    }

    public String getName() {
        return name;
    }

    public UserApiKeyScopes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllowedParameters() {
        return allowedParameters;
    }

    public UserApiKeyScopes allowedParameters(String allowedParameters) {
        this.allowedParameters = allowedParameters;
        return this;
    }

    public void setAllowedParameters(String allowedParameters) {
        this.allowedParameters = allowedParameters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApiKeyScopes)) {
            return false;
        }
        return id != null && id.equals(((UserApiKeyScopes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserApiKeyScopes{" +
            "id=" + getId() +
            ", userApiKeyId=" + getUserApiKeyId() +
            ", name='" + getName() + "'" +
            ", allowedParameters='" + getAllowedParameters() + "'" +
            "}";
    }
}
