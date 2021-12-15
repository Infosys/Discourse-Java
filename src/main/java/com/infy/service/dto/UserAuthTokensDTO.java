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
 * A DTO for the {@link com.infy.domain.UserAuthTokens} entity.
 */
public class UserAuthTokensDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String authToken;

    @NotNull
    private String prevAuthToken;

    private String userAgent;

    @NotNull
    private Boolean authTokenSeen;

    private String clientIp;

    @NotNull
    private Instant rotatedAt;

    private Instant seenAt;


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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPrevAuthToken() {
        return prevAuthToken;
    }

    public void setPrevAuthToken(String prevAuthToken) {
        this.prevAuthToken = prevAuthToken;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Boolean isAuthTokenSeen() {
        return authTokenSeen;
    }

    public void setAuthTokenSeen(Boolean authTokenSeen) {
        this.authTokenSeen = authTokenSeen;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Instant getRotatedAt() {
        return rotatedAt;
    }

    public void setRotatedAt(Instant rotatedAt) {
        this.rotatedAt = rotatedAt;
    }

    public Instant getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(Instant seenAt) {
        this.seenAt = seenAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAuthTokensDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAuthTokensDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAuthTokensDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", authToken='" + getAuthToken() + "'" +
            ", prevAuthToken='" + getPrevAuthToken() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", authTokenSeen='" + isAuthTokenSeen() + "'" +
            ", clientIp='" + getClientIp() + "'" +
            ", rotatedAt='" + getRotatedAt() + "'" +
            ", seenAt='" + getSeenAt() + "'" +
            "}";
    }
}
