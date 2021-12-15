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
 * A DTO for the {@link com.infy.domain.UserExports} entity.
 */
public class UserExportsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String userId;

    private Long uploadId;

    private Long topicId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExportsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserExportsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExportsDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", userId='" + getUserId() + "'" +
            ", uploadId=" + getUploadId() +
            ", topicId=" + getTopicId() +
            "}";
    }
}
