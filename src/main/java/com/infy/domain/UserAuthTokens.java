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
import java.time.Instant;

/**
 * A UserAuthTokens.
 */
@Entity
@Table(name = "user_auth_tokens")
public class UserAuthTokens extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "auth_token", nullable = false)
    private String authToken;

    @NotNull
    @Column(name = "prev_auth_token", nullable = false)
    private String prevAuthToken;

    @Column(name = "user_agent")
    private String userAgent;

    @NotNull
    @Column(name = "auth_token_seen", nullable = false)
    private Boolean authTokenSeen;

    @Column(name = "client_ip")
    private String clientIp;

    @NotNull
    @Column(name = "rotated_at", nullable = false)
    private Instant rotatedAt;

    @Column(name = "seen_at")
    private Instant seenAt;

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

    public UserAuthTokens userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public UserAuthTokens authToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPrevAuthToken() {
        return prevAuthToken;
    }

    public UserAuthTokens prevAuthToken(String prevAuthToken) {
        this.prevAuthToken = prevAuthToken;
        return this;
    }

    public void setPrevAuthToken(String prevAuthToken) {
        this.prevAuthToken = prevAuthToken;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public UserAuthTokens userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Boolean isAuthTokenSeen() {
        return authTokenSeen;
    }

    public UserAuthTokens authTokenSeen(Boolean authTokenSeen) {
        this.authTokenSeen = authTokenSeen;
        return this;
    }

    public void setAuthTokenSeen(Boolean authTokenSeen) {
        this.authTokenSeen = authTokenSeen;
    }

    public String getClientIp() {
        return clientIp;
    }

    public UserAuthTokens clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Instant getRotatedAt() {
        return rotatedAt;
    }

    public UserAuthTokens rotatedAt(Instant rotatedAt) {
        this.rotatedAt = rotatedAt;
        return this;
    }

    public void setRotatedAt(Instant rotatedAt) {
        this.rotatedAt = rotatedAt;
    }

    public Instant getSeenAt() {
        return seenAt;
    }

    public UserAuthTokens seenAt(Instant seenAt) {
        this.seenAt = seenAt;
        return this;
    }

    public void setSeenAt(Instant seenAt) {
        this.seenAt = seenAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAuthTokens)) {
            return false;
        }
        return id != null && id.equals(((UserAuthTokens) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAuthTokens{" +
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
