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
 * A LinkedTopics.
 */
@Entity
@Table(name = "linked_topics")
public class LinkedTopics extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "original_topic_id", nullable = false)
    private Long originalTopicId;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public LinkedTopics topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getOriginalTopicId() {
        return originalTopicId;
    }

    public LinkedTopics originalTopicId(Long originalTopicId) {
        this.originalTopicId = originalTopicId;
        return this;
    }

    public void setOriginalTopicId(Long originalTopicId) {
        this.originalTopicId = originalTopicId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public LinkedTopics sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkedTopics)) {
            return false;
        }
        return id != null && id.equals(((LinkedTopics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkedTopics{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", originalTopicId=" + getOriginalTopicId() +
            ", sequence=" + getSequence() +
            "}";
    }
}
