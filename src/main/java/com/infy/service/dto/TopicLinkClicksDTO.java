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
 * A DTO for the {@link com.infy.domain.TopicLinkClicks} entity.
 */
public class TopicLinkClicksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long topicLinkId;

    private String userId;

    private String ipAddress;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicLinkId() {
        return topicLinkId;
    }

    public void setTopicLinkId(Long topicLinkId) {
        this.topicLinkId = topicLinkId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicLinkClicksDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicLinkClicksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicLinkClicksDTO{" +
            "id=" + getId() +
            ", topicLinkId=" + getTopicLinkId() +
            ", userId='" + getUserId() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            "}";
    }
}
