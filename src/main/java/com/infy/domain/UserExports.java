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
 * A UserExports.
 */
@Entity
@Table(name = "user_exports")
public class UserExports extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "upload_id")
    private Long uploadId;

    @Column(name = "topic_id")
    private Long topicId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public UserExports fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public UserExports userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public UserExports uploadId(Long uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public UserExports topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExports)) {
            return false;
        }
        return id != null && id.equals(((UserExports) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExports{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", userId='" + getUserId() + "'" +
            ", uploadId=" + getUploadId() +
            ", topicId=" + getTopicId() +
            "}";
    }
}