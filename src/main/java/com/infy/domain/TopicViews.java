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
import java.time.LocalDate;

/**
 * A TopicViews.
 */
@Entity
@Table(name = "topic_views")
public class TopicViews extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "viewed_at", nullable = false)
    private LocalDate viewedAt;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip_address")
    private String ipAddress;

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

    public TopicViews topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public LocalDate getViewedAt() {
        return viewedAt;
    }

    public TopicViews viewedAt(LocalDate viewedAt) {
        this.viewedAt = viewedAt;
        return this;
    }

    public void setViewedAt(LocalDate viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getUserId() {
        return userId;
    }

    public TopicViews userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public TopicViews ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicViews)) {
            return false;
        }
        return id != null && id.equals(((TopicViews) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicViews{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", viewedAt='" + getViewedAt() + "'" +
            ", userId='" + getUserId() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            "}";
    }
}
