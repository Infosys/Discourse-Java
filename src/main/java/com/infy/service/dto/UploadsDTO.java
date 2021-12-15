/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.Uploads} entity.
 */
public class UploadsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String originalFilename;

    @NotNull
    private Integer filesize;

    private Integer width;

    private Integer height;

    @NotNull
    private String url;

    private String sha1;

    private String origin;

    private Integer retainHours;

    private String extension;

    private Integer thumbnailWidth;

    private Integer thumbnailHeight;

    private String etag;

    @NotNull
    private Boolean secure;

    private Long accessControlPostId;

    private String originalSha1;

    private Boolean animated;

    private Boolean verified;

    @NotNull
    private Integer verificationStatus;

    private Instant securityLastChangedAt;

    private String securityLastChangedReason;


    private Long userProfilesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getRetainHours() {
        return retainHours;
    }

    public void setRetainHours(Integer retainHours) {
        this.retainHours = retainHours;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Boolean isSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Long getAccessControlPostId() {
        return accessControlPostId;
    }

    public void setAccessControlPostId(Long accessControlPostId) {
        this.accessControlPostId = accessControlPostId;
    }

    public String getOriginalSha1() {
        return originalSha1;
    }

    public void setOriginalSha1(String originalSha1) {
        this.originalSha1 = originalSha1;
    }

    public Boolean isAnimated() {
        return animated;
    }

    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Instant getSecurityLastChangedAt() {
        return securityLastChangedAt;
    }

    public void setSecurityLastChangedAt(Instant securityLastChangedAt) {
        this.securityLastChangedAt = securityLastChangedAt;
    }

    public String getSecurityLastChangedReason() {
        return securityLastChangedReason;
    }

    public void setSecurityLastChangedReason(String securityLastChangedReason) {
        this.securityLastChangedReason = securityLastChangedReason;
    }

    public Long getUserProfilesId() {
        return userProfilesId;
    }

    public void setUserProfilesId(Long userProfilesId) {
        this.userProfilesId = userProfilesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadsDTO)) {
            return false;
        }

        return id != null && id.equals(((UploadsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UploadsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", originalFilename='" + getOriginalFilename() + "'" +
            ", filesize=" + getFilesize() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", url='" + getUrl() + "'" +
            ", sha1='" + getSha1() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", retainHours=" + getRetainHours() +
            ", extension='" + getExtension() + "'" +
            ", thumbnailWidth=" + getThumbnailWidth() +
            ", thumbnailHeight=" + getThumbnailHeight() +
            ", etag='" + getEtag() + "'" +
            ", secure='" + isSecure() + "'" +
            ", accessControlPostId=" + getAccessControlPostId() +
            ", originalSha1='" + getOriginalSha1() + "'" +
            ", animated='" + isAnimated() + "'" +
            ", verified='" + isVerified() + "'" +
            ", verificationStatus=" + getVerificationStatus() +
            ", securityLastChangedAt='" + getSecurityLastChangedAt() + "'" +
            ", securityLastChangedReason='" + getSecurityLastChangedReason() + "'" +
            ", userProfilesId=" + getUserProfilesId() +
            "}";
    }
}
