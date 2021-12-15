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
 * A GroupHistories.
 */
@Entity
@Table(name = "group_histories")
public class GroupHistories extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @NotNull
    @Column(name = "acting_user_id", nullable = false)
    private String actingUserId;

    @Column(name = "target_user_id")
    private String targetUserId;

    @NotNull
    @Column(name = "action", nullable = false)
    private Integer action;

    @Column(name = "subject")
    private String subject;

    @Column(name = "prev_value")
    private String prevValue;

    @Column(name = "new_value")
    private String newValue;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

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

    public GroupHistories groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getActingUserId() {
        return actingUserId;
    }

    public GroupHistories actingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
        return this;
    }

    public void setActingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public GroupHistories targetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
        return this;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getAction() {
        return action;
    }

    public GroupHistories action(Integer action) {
        this.action = action;
        return this;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public GroupHistories subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPrevValue() {
        return prevValue;
    }

    public GroupHistories prevValue(String prevValue) {
        this.prevValue = prevValue;
        return this;
    }

    public void setPrevValue(String prevValue) {
        this.prevValue = prevValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public GroupHistories newValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public GroupHistories updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupHistories)) {
            return false;
        }
        return id != null && id.equals(((GroupHistories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupHistories{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", actingUserId='" + getActingUserId() + "'" +
            ", targetUserId='" + getTargetUserId() + "'" +
            ", action=" + getAction() +
            ", subject='" + getSubject() + "'" +
            ", prevValue='" + getPrevValue() + "'" +
            ", newValue='" + getNewValue() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
