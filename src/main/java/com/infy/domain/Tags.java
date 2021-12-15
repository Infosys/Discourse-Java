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
 * A Tags.
 */
@Entity
@Table(name = "tags")
public class Tags extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "topic_count", nullable = false)
    private Integer topicCount;

    @NotNull
    @Column(name = "pm_topic_count", nullable = false)
    private Integer pmTopicCount;

    @Column(name = "target_tag_id")
    private Long targetTagId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tags name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public Tags topicCount(Integer topicCount) {
        this.topicCount = topicCount;
        return this;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getPmTopicCount() {
        return pmTopicCount;
    }

    public Tags pmTopicCount(Integer pmTopicCount) {
        this.pmTopicCount = pmTopicCount;
        return this;
    }

    public void setPmTopicCount(Integer pmTopicCount) {
        this.pmTopicCount = pmTopicCount;
    }

    public Long getTargetTagId() {
        return targetTagId;
    }

    public Tags targetTagId(Long targetTagId) {
        this.targetTagId = targetTagId;
        return this;
    }

    public void setTargetTagId(Long targetTagId) {
        this.targetTagId = targetTagId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tags)) {
            return false;
        }
        return id != null && id.equals(((Tags) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tags{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", topicCount=" + getTopicCount() +
            ", pmTopicCount=" + getPmTopicCount() +
            ", targetTagId=" + getTargetTagId() +
            "}";
    }
}
