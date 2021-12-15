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
 * A UserAuthTokenLogs.
 */
@Entity
@Table(name = "user_auth_token_logs")
public class UserAuthTokenLogs extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "user_auth_token_id")
    private Long userAuthTokenId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "path")
    private String path;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public UserAuthTokenLogs action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getUserAuthTokenId() {
        return userAuthTokenId;
    }

    public UserAuthTokenLogs userAuthTokenId(Long userAuthTokenId) {
        this.userAuthTokenId = userAuthTokenId;
        return this;
    }

    public void setUserAuthTokenId(Long userAuthTokenId) {
        this.userAuthTokenId = userAuthTokenId;
    }

    public String getUserId() {
        return userId;
    }

    public UserAuthTokenLogs userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public UserAuthTokenLogs clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public UserAuthTokenLogs userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAuthToken() {
        return authToken;
    }

    public UserAuthTokenLogs authToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPath() {
        return path;
    }

    public UserAuthTokenLogs path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAuthTokenLogs)) {
            return false;
        }
        return id != null && id.equals(((UserAuthTokenLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAuthTokenLogs{" +
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
