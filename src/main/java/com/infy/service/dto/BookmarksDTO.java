/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.Bookmarks} entity.
 */
public class BookmarksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Long topicId;

    @NotNull
    private Long postId;

    private String name;

    private Integer reminderType;

    private Instant reminderAt;

    private Instant reminderLastSentAt;

    private Instant reminderSetAt;

    @NotNull
    private Integer autoDeletePreference;

    private Boolean pinned;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReminderType() {
        return reminderType;
    }

    public void setReminderType(Integer reminderType) {
        this.reminderType = reminderType;
    }

    public Instant getReminderAt() {
        return reminderAt;
    }

    public void setReminderAt(Instant reminderAt) {
        this.reminderAt = reminderAt;
    }

    public Instant getReminderLastSentAt() {
        return reminderLastSentAt;
    }

    public void setReminderLastSentAt(Instant reminderLastSentAt) {
        this.reminderLastSentAt = reminderLastSentAt;
    }

    public Instant getReminderSetAt() {
        return reminderSetAt;
    }

    public void setReminderSetAt(Instant reminderSetAt) {
        this.reminderSetAt = reminderSetAt;
    }

    public Integer getAutoDeletePreference() {
        return autoDeletePreference;
    }

    public void setAutoDeletePreference(Integer autoDeletePreference) {
        this.autoDeletePreference = autoDeletePreference;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookmarksDTO)) {
            return false;
        }

        return id != null && id.equals(((BookmarksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookmarksDTO{" +
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
