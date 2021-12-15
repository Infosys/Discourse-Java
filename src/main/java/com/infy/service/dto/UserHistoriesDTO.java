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
 * A DTO for the {@link com.infy.domain.UserHistories} entity.
 */
public class UserHistoriesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer action;

    private String actingUserId;

    private String targetUserId;

    private String details;

    private String context;

    private String ipAddress;

    private String email;

    private String subject;

    private String previousValue;

    private String newValue;

    private Long topicId;

    private Boolean adminOnly;

    private Long postId;

    private String customType;

    private Long categoryId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getActingUserId() {
        return actingUserId;
    }

    public void setActingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean isAdminOnly() {
        return adminOnly;
    }

    public void setAdminOnly(Boolean adminOnly) {
        this.adminOnly = adminOnly;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserHistoriesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserHistoriesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserHistoriesDTO{" +
            "id=" + getId() +
            ", action=" + getAction() +
            ", actingUserId='" + getActingUserId() + "'" +
            ", targetUserId='" + getTargetUserId() + "'" +
            ", details='" + getDetails() + "'" +
            ", context='" + getContext() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", subject='" + getSubject() + "'" +
            ", previousValue='" + getPreviousValue() + "'" +
            ", newValue='" + getNewValue() + "'" +
            ", topicId=" + getTopicId() +
            ", adminOnly='" + isAdminOnly() + "'" +
            ", postId=" + getPostId() +
            ", customType='" + getCustomType() + "'" +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
