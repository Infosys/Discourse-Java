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
 * A DTO for the {@link com.infy.domain.ScreenedIpAddresses} entity.
 */
public class ScreenedIpAddressesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String ipAddress;

    @NotNull
    private Integer actionType;

    @NotNull
    private Integer matchCount;

    private Instant lastMatchAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public Instant getLastMatchAt() {
        return lastMatchAt;
    }

    public void setLastMatchAt(Instant lastMatchAt) {
        this.lastMatchAt = lastMatchAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreenedIpAddressesDTO)) {
            return false;
        }

        return id != null && id.equals(((ScreenedIpAddressesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScreenedIpAddressesDTO{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", actionType=" + getActionType() +
            ", matchCount=" + getMatchCount() +
            ", lastMatchAt='" + getLastMatchAt() + "'" +
            "}";
    }
}
