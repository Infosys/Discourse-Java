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
 * A DTO for the {@link com.infy.domain.CategoryTagStats} entity.
 */
public class CategoryTagStatsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long tagId;

    @NotNull
    private Integer topicCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryTagStatsDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryTagStatsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryTagStatsDTO{" +
            "id=" + getId() +
            ", categoryId=" + getCategoryId() +
            ", tagId=" + getTagId() +
            ", topicCount=" + getTopicCount() +
            "}";
    }
}
