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
 * A TopicThumbnails.
 */
@Entity
@Table(name = "topic_thumbnails")
public class TopicThumbnails extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "upload_id", nullable = false)
    private Long uploadId;

    @Column(name = "optimized_image_id")
    private Long optimizedImageId;

    @NotNull
    @Column(name = "max_width", nullable = false)
    private Integer maxWidth;

    @NotNull
    @Column(name = "max_height", nullable = false)
    private Integer maxHeight;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public TopicThumbnails uploadId(Long uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getOptimizedImageId() {
        return optimizedImageId;
    }

    public TopicThumbnails optimizedImageId(Long optimizedImageId) {
        this.optimizedImageId = optimizedImageId;
        return this;
    }

    public void setOptimizedImageId(Long optimizedImageId) {
        this.optimizedImageId = optimizedImageId;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public TopicThumbnails maxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public TopicThumbnails maxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicThumbnails)) {
            return false;
        }
        return id != null && id.equals(((TopicThumbnails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicThumbnails{" +
            "id=" + getId() +
            ", uploadId=" + getUploadId() +
            ", optimizedImageId=" + getOptimizedImageId() +
            ", maxWidth=" + getMaxWidth() +
            ", maxHeight=" + getMaxHeight() +
            "}";
    }
}
