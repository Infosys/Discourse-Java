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
 * A SingleSignOnRecords.
 */
@Entity
@Table(name = "single_sign_on_records")
public class SingleSignOnRecords extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "external_id", nullable = false)
    private String externalId;

    @NotNull
    @Column(name = "last_payload", nullable = false)
    private String lastPayload;

    @Column(name = "external_username")
    private String externalUsername;

    @Column(name = "external_email")
    private String externalEmail;

    @Column(name = "external_name")
    private String externalName;

    @Column(name = "external_avatar_url")
    private String externalAvatarUrl;

    @Column(name = "external_profile_background_url")
    private String externalProfileBackgroundUrl;

    @Column(name = "external_card_background_url")
    private String externalCardBackgroundUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public SingleSignOnRecords userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExternalId() {
        return externalId;
    }

    public SingleSignOnRecords externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getLastPayload() {
        return lastPayload;
    }

    public SingleSignOnRecords lastPayload(String lastPayload) {
        this.lastPayload = lastPayload;
        return this;
    }

    public void setLastPayload(String lastPayload) {
        this.lastPayload = lastPayload;
    }

    public String getExternalUsername() {
        return externalUsername;
    }

    public SingleSignOnRecords externalUsername(String externalUsername) {
        this.externalUsername = externalUsername;
        return this;
    }

    public void setExternalUsername(String externalUsername) {
        this.externalUsername = externalUsername;
    }

    public String getExternalEmail() {
        return externalEmail;
    }

    public SingleSignOnRecords externalEmail(String externalEmail) {
        this.externalEmail = externalEmail;
        return this;
    }

    public void setExternalEmail(String externalEmail) {
        this.externalEmail = externalEmail;
    }

    public String getExternalName() {
        return externalName;
    }

    public SingleSignOnRecords externalName(String externalName) {
        this.externalName = externalName;
        return this;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public String getExternalAvatarUrl() {
        return externalAvatarUrl;
    }

    public SingleSignOnRecords externalAvatarUrl(String externalAvatarUrl) {
        this.externalAvatarUrl = externalAvatarUrl;
        return this;
    }

    public void setExternalAvatarUrl(String externalAvatarUrl) {
        this.externalAvatarUrl = externalAvatarUrl;
    }

    public String getExternalProfileBackgroundUrl() {
        return externalProfileBackgroundUrl;
    }

    public SingleSignOnRecords externalProfileBackgroundUrl(String externalProfileBackgroundUrl) {
        this.externalProfileBackgroundUrl = externalProfileBackgroundUrl;
        return this;
    }

    public void setExternalProfileBackgroundUrl(String externalProfileBackgroundUrl) {
        this.externalProfileBackgroundUrl = externalProfileBackgroundUrl;
    }

    public String getExternalCardBackgroundUrl() {
        return externalCardBackgroundUrl;
    }

    public SingleSignOnRecords externalCardBackgroundUrl(String externalCardBackgroundUrl) {
        this.externalCardBackgroundUrl = externalCardBackgroundUrl;
        return this;
    }

    public void setExternalCardBackgroundUrl(String externalCardBackgroundUrl) {
        this.externalCardBackgroundUrl = externalCardBackgroundUrl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SingleSignOnRecords)) {
            return false;
        }
        return id != null && id.equals(((SingleSignOnRecords) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SingleSignOnRecords{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", lastPayload='" + getLastPayload() + "'" +
            ", externalUsername='" + getExternalUsername() + "'" +
            ", externalEmail='" + getExternalEmail() + "'" +
            ", externalName='" + getExternalName() + "'" +
            ", externalAvatarUrl='" + getExternalAvatarUrl() + "'" +
            ", externalProfileBackgroundUrl='" + getExternalProfileBackgroundUrl() + "'" +
            ", externalCardBackgroundUrl='" + getExternalCardBackgroundUrl() + "'" +
            "}";
    }
}
