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
import java.time.Instant;

/**
 * A Bookmarks.
 */
@Entity
@Table(name = "bookmarks")
public class Bookmarks extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "name")
    private String name;

    @Column(name = "reminder_type")
    private Integer reminderType;

    @Column(name = "reminder_at")
    private Instant reminderAt;

    @Column(name = "reminder_last_sent_at")
    private Instant reminderLastSentAt;

    @Column(name = "reminder_set_at")
    private Instant reminderSetAt;

    @NotNull
    @Column(name = "auto_delete_preference", nullable = false)
    private Integer autoDeletePreference;

    @Column(name = "pinned")
    private Boolean pinned;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Bookmarks userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Bookmarks topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostId() {
        return postId;
    }

    public Bookmarks postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public Bookmarks name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReminderType() {
        return reminderType;
    }

    public Bookmarks reminderType(Integer reminderType) {
        this.reminderType = reminderType;
        return this;
    }

    public void setReminderType(Integer reminderType) {
        this.reminderType = reminderType;
    }

    public Instant getReminderAt() {
        return reminderAt;
    }

    public Bookmarks reminderAt(Instant reminderAt) {
        this.reminderAt = reminderAt;
        return this;
    }

    public void setReminderAt(Instant reminderAt) {
        this.reminderAt = reminderAt;
    }

    public Instant getReminderLastSentAt() {
        return reminderLastSentAt;
    }

    public Bookmarks reminderLastSentAt(Instant reminderLastSentAt) {
        this.reminderLastSentAt = reminderLastSentAt;
        return this;
    }

    public void setReminderLastSentAt(Instant reminderLastSentAt) {
        this.reminderLastSentAt = reminderLastSentAt;
    }

    public Instant getReminderSetAt() {
        return reminderSetAt;
    }

    public Bookmarks reminderSetAt(Instant reminderSetAt) {
        this.reminderSetAt = reminderSetAt;
        return this;
    }

    public void setReminderSetAt(Instant reminderSetAt) {
        this.reminderSetAt = reminderSetAt;
    }

    public Integer getAutoDeletePreference() {
        return autoDeletePreference;
    }

    public Bookmarks autoDeletePreference(Integer autoDeletePreference) {
        this.autoDeletePreference = autoDeletePreference;
        return this;
    }

    public void setAutoDeletePreference(Integer autoDeletePreference) {
        this.autoDeletePreference = autoDeletePreference;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public Bookmarks pinned(Boolean pinned) {
        this.pinned = pinned;
        return this;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bookmarks)) {
            return false;
        }
        return id != null && id.equals(((Bookmarks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bookmarks{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            ", name='" + getName() + "'" +
            ", reminderType=" + getReminderType() +
            ", reminderAt='" + getReminderAt() + "'" +
            ", reminderLastSentAt='" + getReminderLastSentAt() + "'" +
            ", reminderSetAt='" + getReminderSetAt() + "'" +
            ", autoDeletePreference=" + getAutoDeletePreference() +
            ", pinned='" + isPinned() + "'" +
            "}";
    }
}
