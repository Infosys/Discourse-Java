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
 * A DTO for the {@link com.infy.domain.TopicGroups} entity.
 */
public class TopicGroupsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long groupId;

    @NotNull
    private Long topicId;

    @NotNull
    private Long lastReadPostNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getLastReadPostNumber() {
        return lastReadPostNumber;
    }

    public void setLastReadPostNumber(Long lastReadPostNumber) {
        this.lastReadPostNumber = lastReadPostNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicGroupsDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicGroupsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicGroupsDTO{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", topicId=" + getTopicId() +
            ", lastReadPostNumber=" + getLastReadPostNumber() +
            "}";
    }
}
