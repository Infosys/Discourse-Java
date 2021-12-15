/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.EmailTokens} entity.
 */
public class EmailTokensDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String email;

    @NotNull
    private String token;

    @NotNull
    private Boolean confirmed;

    @NotNull
    private Boolean expired;

    @NotNull
    private Instant updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean isExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTokensDTO)) {
            return false;
        }

        return id != null && id.equals(((EmailTokensDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailTokensDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", email='" + getEmail() + "'" +
            ", token='" + getToken() + "'" +
            ", confirmed='" + isConfirmed() + "'" +
            ", expired='" + isExpired() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
