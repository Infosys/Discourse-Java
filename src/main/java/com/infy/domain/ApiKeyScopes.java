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
 * A ApiKeyScopes.
 */
@Entity
@Table(name = "api_key_scopes")
public class ApiKeyScopes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "api_key_id", nullable = false)
    private Long apiKeyId;

    @NotNull
    @Column(name = "resource", nullable = false)
    private String resource;

    @NotNull
    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "allowed_parameters")
    private String allowedParameters;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiKeyId() {
        return apiKeyId;
    }

    public ApiKeyScopes apiKeyId(Long apiKeyId) {
        this.apiKeyId = apiKeyId;
        return this;
    }

    public void setApiKeyId(Long apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public String getResource() {
        return resource;
    }

    public ApiKeyScopes resource(String resource) {
        this.resource = resource;
        return this;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public ApiKeyScopes action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAllowedParameters() {
        return allowedParameters;
    }

    public ApiKeyScopes allowedParameters(String allowedParameters) {
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
        if (!(o instanceof ApiKeyScopes)) {
            return false;
        }
        return id != null && id.equals(((ApiKeyScopes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiKeyScopes{" +
            "id=" + getId() +
            ", apiKeyId=" + getApiKeyId() +
            ", resource='" + getResource() + "'" +
            ", action='" + getAction() + "'" +
            ", allowedParameters='" + getAllowedParameters() + "'" +
            "}";
    }
}
