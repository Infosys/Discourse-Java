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
 * A DTO for the {@link com.infy.domain.UserApiKeyScopes} entity.
 */
public class UserApiKeyScopesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userApiKeyId;

    @NotNull
    private String name;

    private String allowedParameters;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserApiKeyId() {
        return userApiKeyId;
    }

    public void setUserApiKeyId(Long userApiKeyId) {
        this.userApiKeyId = userApiKeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof UserApiKeyScopesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserApiKeyScopesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserApiKeyScopesDTO{" +
            "id=" + getId() +
            ", userApiKeyId=" + getUserApiKeyId() +
            ", name='" + getName() + "'" +
            ", allowedParameters='" + getAllowedParameters() + "'" +
            "}";
    }
}
