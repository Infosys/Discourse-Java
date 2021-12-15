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
 * A Notifications.
 */
@Entity
@Table(name = "notifications")
public class Notifications extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "notification_type", nullable = false)
    private Integer notificationType;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "data", nullable = false)
    private String data;

    @NotNull
    @Column(name = "read", nullable = false)
    private Boolean read;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "post_number")
    private Integer postNumber;

    @Column(name = "post_action_id")
    private Long postActionId;

    @NotNull
    @Column(name = "high_priority", nullable = false)
    private Boolean highPriority;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNotificationType() {
        return notificationType;
    }

    public Notifications notificationType(Integer notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public String getUserId() {
        return userId;
    }

    public Notifications userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public Notifications data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean isRead() {
        return read;
    }

    public Notifications read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Notifications topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public Notifications postNumber(Integer postNumber) {
        this.postNumber = postNumber;
        return this;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public Long getPostActionId() {
        return postActionId;
    }

    public Notifications postActionId(Long postActionId) {
        this.postActionId = postActionId;
        return this;
    }

    public void setPostActionId(Long postActionId) {
        this.postActionId = postActionId;
    }

    public Boolean isHighPriority() {
        return highPriority;
    }

    public Notifications highPriority(Boolean highPriority) {
        this.highPriority = highPriority;
        return this;
    }

    public void setHighPriority(Boolean highPriority) {
        this.highPriority = highPriority;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notifications)) {
            return false;
        }
        return id != null && id.equals(((Notifications) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notifications{" +
            "id=" + getId() +
            ", notificationType=" + getNotificationType() +
            ", userId='" + getUserId() + "'" +
            ", data='" + getData() + "'" +
            ", read='" + isRead() + "'" +
            ", topicId=" + getTopicId() +
            ", postNumber=" + getPostNumber() +
            ", postActionId=" + getPostActionId() +
            ", highPriority='" + isHighPriority() + "'" +
            "}";
    }
}
