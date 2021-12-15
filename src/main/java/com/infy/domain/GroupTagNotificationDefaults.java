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
 * A GroupTagNotificationDefaults.
 */
@Entity
@Table(name = "group_tag_notification_defaults")
public class GroupTagNotificationDefaults extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @NotNull
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @NotNull
    @Column(name = "notification_level", nullable = false)
    private Integer notificationLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GroupTagNotificationDefaults groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTagId() {
        return tagId;
    }

    public GroupTagNotificationDefaults tagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public GroupTagNotificationDefaults notificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
        return this;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupTagNotificationDefaults)) {
            return false;
        }
        return id != null && id.equals(((GroupTagNotificationDefaults) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupTagNotificationDefaults{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", tagId=" + getTagId() +
            ", notificationLevel=" + getNotificationLevel() +
            "}";
    }
}
