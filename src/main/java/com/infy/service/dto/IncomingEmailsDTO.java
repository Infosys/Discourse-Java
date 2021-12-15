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
 * A DTO for the {@link com.infy.domain.IncomingEmails} entity.
 */
public class IncomingEmailsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String userId;

    private Long topicId;

    private Long postId;

    private String raw;

    private String error;

    private String messageId;

    private String fromAddress;

    private String toAddresses;

    private String ccAddresses;

    private String subject;

    private String rejectionMessage;

    private Boolean isAutoGenerated;

    @NotNull
    private Boolean isBounce;

    private Integer imapUidValidity;

    private Integer imapUid;

    private Boolean imapSync;

    private Long imapGroupId;

    @NotNull
    private Boolean imapMissing;

    @NotNull
    private Integer createdVia;


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

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(String ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRejectionMessage() {
        return rejectionMessage;
    }

    public void setRejectionMessage(String rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }

    public Boolean isIsAutoGenerated() {
        return isAutoGenerated;
    }

    public void setIsAutoGenerated(Boolean isAutoGenerated) {
        this.isAutoGenerated = isAutoGenerated;
    }

    public Boolean isIsBounce() {
        return isBounce;
    }

    public void setIsBounce(Boolean isBounce) {
        this.isBounce = isBounce;
    }

    public Integer getImapUidValidity() {
        return imapUidValidity;
    }

    public void setImapUidValidity(Integer imapUidValidity) {
        this.imapUidValidity = imapUidValidity;
    }

    public Integer getImapUid() {
        return imapUid;
    }

    public void setImapUid(Integer imapUid) {
        this.imapUid = imapUid;
    }

    public Boolean isImapSync() {
        return imapSync;
    }

    public void setImapSync(Boolean imapSync) {
        this.imapSync = imapSync;
    }

    public Long getImapGroupId() {
        return imapGroupId;
    }

    public void setImapGroupId(Long imapGroupId) {
        this.imapGroupId = imapGroupId;
    }

    public Boolean isImapMissing() {
        return imapMissing;
    }

    public void setImapMissing(Boolean imapMissing) {
        this.imapMissing = imapMissing;
    }

    public Integer getCreatedVia() {
        return createdVia;
    }

    public void setCreatedVia(Integer createdVia) {
        this.createdVia = createdVia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingEmailsDTO)) {
            return false;
        }

        return id != null && id.equals(((IncomingEmailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingEmailsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            ", raw='" + getRaw() + "'" +
            ", error='" + getError() + "'" +
            ", messageId='" + getMessageId() + "'" +
            ", fromAddress='" + getFromAddress() + "'" +
            ", toAddresses='" + getToAddresses() + "'" +
            ", ccAddresses='" + getCcAddresses() + "'" +
            ", subject='" + getSubject() + "'" +
            ", rejectionMessage='" + getRejectionMessage() + "'" +
            ", isAutoGenerated='" + isIsAutoGenerated() + "'" +
            ", isBounce='" + isIsBounce() + "'" +
            ", imapUidValidity=" + getImapUidValidity() +
            ", imapUid=" + getImapUid() +
            ", imapSync='" + isImapSync() + "'" +
            ", imapGroupId=" + getImapGroupId() +
            ", imapMissing='" + isImapMissing() + "'" +
            ", createdVia=" + getCreatedVia() +
            "}";
    }
}