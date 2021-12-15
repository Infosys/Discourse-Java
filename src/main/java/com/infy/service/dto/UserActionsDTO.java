/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.UserActions} entity.
 */
public class UserActionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer actionType;

    @NotNull
    private String userId;

    private Long targetTopicId;

    private Long targetPostId;

    private String targetUserId;

    private String actingUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTargetTopicId() {
        return targetTopicId;
    }

    public void setTargetTopicId(Long targetTopicId) {
        this.targetTopicId = targetTopicId;
    }

    public Long getTargetPostId() {
        return targetPostId;
    }

    public void setTargetPostId(Long targetPostId) {
        this.targetPostId = targetPostId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getActingUserId() {
        return actingUserId;
    }

    public void setActingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActionsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserActionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActionsDTO{" +
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
