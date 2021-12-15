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
 * A DTO for the {@link com.infy.domain.IncomingLinks} entity.
 */
public class IncomingLinksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String userId;

    private String ipAddress;

    private String currentUserId;

    @NotNull
    private Long postId;

    private Long incomingRefererId;


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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getIncomingRefererId() {
        return incomingRefererId;
    }

    public void setIncomingRefererId(Long incomingRefererId) {
        this.incomingRefererId = incomingRefererId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingLinksDTO)) {
            return false;
        }

        return id != null && id.equals(((IncomingLinksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingLinksDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", currentUserId='" + getCurrentUserId() + "'" +
            ", postId=" + getPostId() +
            ", incomingRefererId=" + getIncomingRefererId() +
            "}";
    }
}
