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
 * A CategoryTagStats.
 */
@Entity
@Table(name = "category_tag_stats")
public class CategoryTagStats extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @NotNull
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @NotNull
    @Column(name = "topic_count", nullable = false)
    private Integer topicCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public CategoryTagStats categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTagId() {
        return tagId;
    }

    public CategoryTagStats tagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public CategoryTagStats topicCount(Integer topicCount) {
        this.topicCount = topicCount;
        return this;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryTagStats)) {
            return false;
        }
        return id != null && id.equals(((CategoryTagStats) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryTagStats{" +
            "id=" + getId() +
            ", categoryId=" + getCategoryId() +
            ", tagId=" + getTagId() +
            ", topicCount=" + getTopicCount() +
            "}";
    }
}
