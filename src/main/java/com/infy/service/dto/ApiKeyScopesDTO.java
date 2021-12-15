/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.ApiKeyScopes} entity.
 */
public class ApiKeyScopesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long apiKeyId;

    @NotNull
    private String resource;

    @NotNull
    private String action;

    private String allowedParameters;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiKeyId() {
        return apiKeyId;
    }

    public void setApiKeyId(Long apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAllowedParameters() {
        return allowedParameters;
    }

    public void setAllowedParameters(String allowedParameters) {
        this.allowedParameters = allowedParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKeyScopesDTO)) {
            return false;
        }

        return id != null && id.equals(((ApiKeyScopesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiKeyScopesDTO{" +
            "id=" + getId() +
            ", apiKeyId=" + getApiKeyId() +
            ", resource='" + getResource() + "'" +
            ", action='" + getAction() + "'" +
            ", allowedParameters='" + getAllowedParameters() + "'" +
            "}";
    }
}
