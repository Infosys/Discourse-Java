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
 * A SkippedEmailLogs.
 */
@Entity
@Table(name = "skipped_email_logs")
public class SkippedEmailLogs extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "email_type", nullable = false)
    private String emailType;

    @NotNull
    @Column(name = "to_address", nullable = false)
    private String toAddress;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "post_id")
    private Long postId;

    @NotNull
    @Column(name = "reason_type", nullable = false)
    private Integer reasonType;

    @Column(name = "custom_reason")
    private String customReason;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailType() {
        return emailType;
    }

    public SkippedEmailLogs emailType(String emailType) {
        this.emailType = emailType;
        return this;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getToAddress() {
        return toAddress;
    }

    public SkippedEmailLogs toAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserId() {
        return userId;
    }

    public SkippedEmailLogs userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public SkippedEmailLogs postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Integer getReasonType() {
        return reasonType;
    }

    public SkippedEmailLogs reasonType(Integer reasonType) {
        this.reasonType = reasonType;
        return this;
    }

    public void setReasonType(Integer reasonType) {
        this.reasonType = reasonType;
    }

    public String getCustomReason() {
        return customReason;
    }

    public SkippedEmailLogs customReason(String customReason) {
        this.customReason = customReason;
        return this;
    }

    public void setCustomReason(String customReason) {
        this.customReason = customReason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkippedEmailLogs)) {
            return false;
        }
        return id != null && id.equals(((SkippedEmailLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkippedEmailLogs{" +
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
