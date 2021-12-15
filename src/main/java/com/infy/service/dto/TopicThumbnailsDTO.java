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
 * A DTO for the {@link com.infy.domain.TopicThumbnails} entity.
 */
public class TopicThumbnailsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long uploadId;

    private Long optimizedImageId;

    @NotNull
    private Integer maxWidth;

    @NotNull
    private Integer maxHeight;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getOptimizedImageId() {
        return optimizedImageId;
    }

    public void setOptimizedImageId(Long optimizedImageId) {
        this.optimizedImageId = optimizedImageId;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicThumbnailsDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicThumbnailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicThumbnailsDTO{" +
            "id=" + getId() +
            ", uploadId=" + getUploadId() +
            ", optimizedImageId=" + getOptimizedImageId() +
            ", maxWidth=" + getMaxWidth() +
            ", maxHeight=" + getMaxHeight() +
            "}";
    }
}
