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
 * A PostTimings.
 */
@Entity
@Table(name = "post_timings")
public class PostTimings extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "post_number", nullable = false)
    private Integer postNumber;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "msecs", nullable = false)
    private Integer msecs;

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

    public PostTimings topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public PostTimings postNumber(Integer postNumber) {
        this.postNumber = postNumber;
        return this;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public String getUserId() {
        return userId;
    }

    public PostTimings userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMsecs() {
        return msecs;
    }

    public PostTimings msecs(Integer msecs) {
        this.msecs = msecs;
        return this;
    }

    public void setMsecs(Integer msecs) {
        this.msecs = msecs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostTimings)) {
            return false;
        }
        return id != null && id.equals(((PostTimings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostTimings{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", postNumber=" + getPostNumber() +
            ", userId='" + getUserId() + "'" +
            ", msecs=" + getMsecs() +
            "}";
    }
}
