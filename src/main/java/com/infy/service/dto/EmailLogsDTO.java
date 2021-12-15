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
 * A DTO for the {@link com.infy.domain.EmailLogs} entity.
 */
public class EmailLogsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String toAddress;

    @NotNull
    private String emailType;

    private String userId;

    private Long postId;

    private String bounceKey;

    @NotNull
    private Boolean bounced;

    private String messageId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getBounceKey() {
        return bounceKey;
    }

    public void setBounceKey(String bounceKey) {
        this.bounceKey = bounceKey;
    }

    public Boolean isBounced() {
        return bounced;
    }

    public void setBounced(Boolean bounced) {
        this.bounced = bounced;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((EmailLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailLogsDTO{" +
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
