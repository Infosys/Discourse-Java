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
 * A DTO for the {@link com.infy.domain.SkippedEmailLogs} entity.
 */
public class SkippedEmailLogsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String emailType;

    @NotNull
    private String toAddress;

    private String userId;

    private Long postId;

    @NotNull
    private Integer reasonType;

    private String customReason;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
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

    public Integer getReasonType() {
        return reasonType;
    }

    public void setReasonType(Integer reasonType) {
        this.reasonType = reasonType;
    }

    public String getCustomReason() {
        return customReason;
    }

    public void setCustomReason(String customReason) {
        this.customReason = customReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkippedEmailLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((SkippedEmailLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkippedEmailLogsDTO{" +
            "id=" + getId() +
            ", emailType='" + getEmailType() + "'" +
            ", toAddress='" + getToAddress() + "'" +
            ", userId='" + getUserId() + "'" +
            ", postId=" + getPostId() +
            ", reasonType=" + getReasonType() +
            ", customReason='" + getCustomReason() + "'" +
            "}";
    }
}
