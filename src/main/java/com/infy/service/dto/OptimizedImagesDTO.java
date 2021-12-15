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
 * A DTO for the {@link com.infy.domain.OptimizedImages} entity.
 */
public class OptimizedImagesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String sha1;

    @NotNull
    private String extension;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private Long uploadId;

    @NotNull
    private String url;

    private Integer filesize;

    private String etag;

    private Integer version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptimizedImagesDTO)) {
            return false;
        }

        return id != null && id.equals(((OptimizedImagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptimizedImagesDTO{" +
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
