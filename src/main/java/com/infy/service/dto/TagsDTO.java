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
 * A DTO for the {@link com.infy.domain.Tags} entity.
 */
public class TagsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer topicCount;

    @NotNull
    private Integer pmTopicCount;

    private Long targetTagId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getPmTopicCount() {
        return pmTopicCount;
    }

    public void setPmTopicCount(Integer pmTopicCount) {
        this.pmTopicCount = pmTopicCount;
    }

    public Long getTargetTagId() {
        return targetTagId;
    }

    public void setTargetTagId(Long targetTagId) {
        this.targetTagId = targetTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagsDTO)) {
            return false;
        }

        return id != null && id.equals(((TagsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", topicCount=" + getTopicCount() +
            ", pmTopicCount=" + getPmTopicCount() +
            ", targetTagId=" + getTargetTagId() +
            "}";
    }
}
