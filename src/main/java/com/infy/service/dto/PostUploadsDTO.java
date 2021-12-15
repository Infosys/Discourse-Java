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
 * A DTO for the {@link com.infy.domain.PostUploads} entity.
 */
public class PostUploadsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private Long uploadId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostUploadsDTO)) {
            return false;
        }

        return id != null && id.equals(((PostUploadsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostUploadsDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", uploadId=" + getUploadId() +
            "}";
    }
}
