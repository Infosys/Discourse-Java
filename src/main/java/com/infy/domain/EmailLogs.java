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
 * A EmailLogs.
 */
@Entity
@Table(name = "email_logs")
public class EmailLogs extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @NotNull
    @Column(name = "email_type", nullable = false)
    private String emailType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "bounce_key")
    private String bounceKey;

    @NotNull
    @Column(name = "bounced", nullable = false)
    private Boolean bounced;

    @Column(name = "message_id")
    private String messageId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public EmailLogs toAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getEmailType() {
        return emailType;
    }

    public EmailLogs emailType(String emailType) {
        this.emailType = emailType;
        return this;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getUserId() {
        return userId;
    }

    public EmailLogs userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public EmailLogs postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getBounceKey() {
        return bounceKey;
    }

    public EmailLogs bounceKey(String bounceKey) {
        this.bounceKey = bounceKey;
        return this;
    }

    public void setBounceKey(String bounceKey) {
        this.bounceKey = bounceKey;
    }

    public Boolean isBounced() {
        return bounced;
    }

    public EmailLogs bounced(Boolean bounced) {
        this.bounced = bounced;
        return this;
    }

    public void setBounced(Boolean bounced) {
        this.bounced = bounced;
    }

    public String getMessageId() {
        return messageId;
    }

    public EmailLogs messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailLogs)) {
            return false;
        }
        return id != null && id.equals(((EmailLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailLogs{" +
            "id=" + getId() +
            ", toAddress='" + getToAddress() + "'" +
            ", emailType='" + getEmailType() + "'" +
            ", userId='" + getUserId() + "'" +
            ", postId=" + getPostId() +
            ", bounceKey='" + getBounceKey() + "'" +
            ", bounced='" + isBounced() + "'" +
            ", messageId='" + getMessageId() + "'" +
            "}";
    }
}
