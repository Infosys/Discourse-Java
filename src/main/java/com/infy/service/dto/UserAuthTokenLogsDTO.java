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
 * A DTO for the {@link com.infy.domain.UserAuthTokenLogs} entity.
 */
public class UserAuthTokenLogsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String action;

    private Long userAuthTokenId;

    private String userId;

    private String clientIp;

    private String userAgent;

    private String authToken;

    private String path;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getUserAuthTokenId() {
        return userAuthTokenId;
    }

    public void setUserAuthTokenId(Long userAuthTokenId) {
        this.userAuthTokenId = userAuthTokenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAuthTokenLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAuthTokenLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAuthTokenLogsDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", userAuthTokenId=" + getUserAuthTokenId() +
            ", userId='" + getUserId() + "'" +
            ", clientIp='" + getClientIp() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", authToken='" + getAuthToken() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
