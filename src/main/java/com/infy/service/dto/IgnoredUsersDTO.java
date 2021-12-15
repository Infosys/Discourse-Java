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
 * A DTO for the {@link com.infy.domain.IgnoredUsers} entity.
 */
public class IgnoredUsersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String ignoredUserId;

    private Instant summarizedAt;

    @NotNull
    private Instant expiringAt;


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

    public String getIgnoredUserId() {
        return ignoredUserId;
    }

    public void setIgnoredUserId(String ignoredUserId) {
        this.ignoredUserId = ignoredUserId;
    }

    public Instant getSummarizedAt() {
        return summarizedAt;
    }

    public void setSummarizedAt(Instant summarizedAt) {
        this.summarizedAt = summarizedAt;
    }

    public Instant getExpiringAt() {
        return expiringAt;
    }

    public void setExpiringAt(Instant expiringAt) {
        this.expiringAt = expiringAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IgnoredUsersDTO)) {
            return false;
        }

        return id != null && id.equals(((IgnoredUsersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IgnoredUsersDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", ignoredUserId='" + getIgnoredUserId() + "'" +
            ", summarizedAt='" + getSummarizedAt() + "'" +
            ", expiringAt='" + getExpiringAt() + "'" +
            "}";
    }
}
