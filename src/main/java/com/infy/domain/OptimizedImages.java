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
 * A OptimizedImages.
 */
@Entity
@Table(name = "optimized_images")
public class OptimizedImages extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sha_1", nullable = false)
    private String sha1;

    @NotNull
    @Column(name = "extension", nullable = false)
    private String extension;

    @NotNull
    @Column(name = "width", nullable = false)
    private Integer width;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "upload_id", nullable = false)
    private Long uploadId;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "filesize")
    private Integer filesize;

    @Column(name = "etag")
    private String etag;

    @Column(name = "version")
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSha1() {
        return sha1;
    }

    public OptimizedImages sha1(String sha1) {
        this.sha1 = sha1;
        return this;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getExtension() {
        return extension;
    }

    public OptimizedImages extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getWidth() {
        return width;
    }

    public OptimizedImages width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public OptimizedImages height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public OptimizedImages uploadId(Long uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getUrl() {
        return url;
    }

    public OptimizedImages url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public OptimizedImages filesize(Integer filesize) {
        this.filesize = filesize;
        return this;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public String getEtag() {
        return etag;
    }

    public OptimizedImages etag(String etag) {
        this.etag = etag;
        return this;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Integer getVersion() {
        return version;
    }

    public OptimizedImages version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptimizedImages)) {
            return false;
        }
        return id != null && id.equals(((OptimizedImages) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptimizedImages{" +
            "id=" + getId() +
            ", sha1='" + getSha1() + "'" +
            ", extension='" + getExtension() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", uploadId=" + getUploadId() +
            ", url='" + getUrl() + "'" +
            ", filesize=" + getFilesize() +
            ", etag='" + getEtag() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
