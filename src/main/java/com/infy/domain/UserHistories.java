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
 * A UserHistories.
 */
@Entity
@Table(name = "user_histories")
public class UserHistories extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "action", nullable = false)
    private Integer action;

    @Column(name = "acting_user_id")
    private String actingUserId;

    @Column(name = "target_user_id")
    private String targetUserId;

    @Column(name = "details")
    private String details;

    @Column(name = "context")
    private String context;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "subject")
    private String subject;

    @Column(name = "previous_value")
    private String previousValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "admin_only")
    private Boolean adminOnly;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "custom_type")
    private String customType;

    @Column(name = "category_id")
    private Long categoryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAction() {
        return action;
    }

    public UserHistories action(Integer action) {
        this.action = action;
        return this;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getActingUserId() {
        return actingUserId;
    }

    public UserHistories actingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
        return this;
    }

    public void setActingUserId(String actingUserId) {
        this.actingUserId = actingUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public UserHistories targetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
        return this;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getDetails() {
        return details;
    }

    public UserHistories details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getContext() {
        return context;
    }

    public UserHistories context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public UserHistories ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEmail() {
        return email;
    }

    public UserHistories email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public UserHistories subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public UserHistories previousValue(String previousValue) {
        this.previousValue = previousValue;
        return this;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public UserHistories newValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Long getTopicId() {
        return topicId;
    }

    public UserHistories topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Boolean isAdminOnly() {
        return adminOnly;
    }

    public UserHistories adminOnly(Boolean adminOnly) {
        this.adminOnly = adminOnly;
        return this;
    }

    public void setAdminOnly(Boolean adminOnly) {
        this.adminOnly = adminOnly;
    }

    public Long getPostId() {
        return postId;
    }

    public UserHistories postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCustomType() {
        return customType;
    }

    public UserHistories customType(String customType) {
        this.customType = customType;
        return this;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public UserHistories categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserHistories)) {
            return false;
        }
        return id != null && id.equals(((UserHistories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserHistories{" +
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
