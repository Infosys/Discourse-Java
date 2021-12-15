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
 * A UserActions.
 */
@Entity
@Table(name = "user_actions")
public class UserActions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "action_type", nullable = false)
    private Integer actionType;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "target_topic_id")
    private Long targetTopicId;

    @Column(name = "target_post_id")
    private Long targetPostId;

    @Column(name = "target_user_id")
    private String targetUserId;

    @Column(name = "acting_user_id")
    private String actingUserId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActionType() {
        return actionType;
    }

    public UserActions actionType(Integer actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getUserId() {
        return userId;
    }

    public UserActions userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTargetTopicId() {
        return targetTopicId;
    }

    public UserActions targetTopicId(Long targetTopicId) {
        this.targetTopicId = targetTopicId;
        return this;
    }

    public void setTargetTopicId(Long targetTopicId) {
        this.targetTopicId = targetTopicId;
    }

    public Long getTargetPostId() {
        return targetPostId;
    }

    public UserActions targetPostId(Long targetPostId) {
        this.targetPostId = targetPostId;
        return this;
    }

    public void setTargetPostId(Long targetPostId) {
        this.targetPostId = targetPostId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public UserActions targetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
        return this;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getActingUserId() {
        return actingUserId;
    }

    public UserActions actingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
        return this;
    }

    public void setActingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActions)) {
            return false;
        }
        return id != null && id.equals(((UserActions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActions{" +
            "id=" + getId() +
            ", actionType=" + getActionType() +
            ", userId='" + getUserId() + "'" +
            ", targetTopicId=" + getTargetTopicId() +
            ", targetPostId=" + getTargetPostId() +
            ", targetUserId='" + getTargetUserId() + "'" +
            ", actingUserId='" + getActingUserId() + "'" +
            "}";
    }
}
