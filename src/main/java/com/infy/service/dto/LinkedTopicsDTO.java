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
 * A DTO for the {@link com.infy.domain.LinkedTopics} entity.
 */
public class LinkedTopicsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long topicId;

    @NotNull
    private Long originalTopicId;

    @NotNull
    private Integer sequence;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getOriginalTopicId() {
        return originalTopicId;
    }

    public void setOriginalTopicId(Long originalTopicId) {
        this.originalTopicId = originalTopicId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkedTopicsDTO)) {
            return false;
        }

        return id != null && id.equals(((LinkedTopicsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkedTopicsDTO{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", originalTopicId=" + getOriginalTopicId() +
            ", sequence=" + getSequence() +
            "}";
    }
}
