/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Uploads.
 */
@Entity
@Table(name = "uploads")
public class Uploads extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @NotNull
    @Column(name = "filesize", nullable = false)
    private Integer filesize;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "sha_1")
    private String sha1;

    @Column(name = "origin")
    private String origin;

    @Column(name = "retain_hours")
    private Integer retainHours;

    @Column(name = "extension")
    private String extension;

    @Column(name = "thumbnail_width")
    private Integer thumbnailWidth;

    @Column(name = "thumbnail_height")
    private Integer thumbnailHeight;

    @Column(name = "etag")
    private String etag;

    @NotNull
    @Column(name = "secure", nullable = false)
    private Boolean secure;

    @Column(name = "access_control_post_id")
    private Long accessControlPostId;

    @Column(name = "original_sha_1")
    private String originalSha1;

    @Column(name = "animated")
    private Boolean animated;

    @Column(name = "verified")
    private Boolean verified;

    @NotNull
    @Column(name = "verification_status", nullable = false)
    private Integer verificationStatus;

    @Column(name = "security_last_changed_at")
    private Instant securityLastChangedAt;

    @Column(name = "security_last_changed_reason")
    private String securityLastChangedReason;

    @ManyToOne
    @JsonIgnoreProperties(value = "uploads", allowSetters = true)
    private UserProfiles userProfiles;

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

    public Uploads userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Uploads originalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public Uploads filesize(Integer filesize) {
        this.filesize = filesize;
        return this;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Integer getWidth() {
        return width;
    }

    public Uploads width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Uploads height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public Uploads url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSha1() {
        return sha1;
    }

    public Uploads sha1(String sha1) {
        this.sha1 = sha1;
        return this;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getOrigin() {
        return origin;
    }

    public Uploads origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getRetainHours() {
        return retainHours;
    }

    public Uploads retainHours(Integer retainHours) {
        this.retainHours = retainHours;
        return this;
    }

    public void setRetainHours(Integer retainHours) {
        this.retainHours = retainHours;
    }

    public String getExtension() {
        return extension;
    }

    public Uploads extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public Uploads thumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
        return this;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public Uploads thumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
        return this;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getEtag() {
        return etag;
    }

    public Uploads etag(String etag) {
        this.etag = etag;
        return this;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Boolean isSecure() {
        return secure;
    }

    public Uploads secure(Boolean secure) {
        this.secure = secure;
        return this;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Long getAccessControlPostId() {
        return accessControlPostId;
    }

    public Uploads accessControlPostId(Long accessControlPostId) {
        this.accessControlPostId = accessControlPostId;
        return this;
    }

    public void setAccessControlPostId(Long accessControlPostId) {
        this.accessControlPostId = accessControlPostId;
    }

    public String getOriginalSha1() {
        return originalSha1;
    }

    public Uploads originalSha1(String originalSha1) {
        this.originalSha1 = originalSha1;
        return this;
    }

    public void setOriginalSha1(String originalSha1) {
        this.originalSha1 = originalSha1;
    }

    public Boolean isAnimated() {
        return animated;
    }

    public Uploads animated(Boolean animated) {
        this.animated = animated;
        return this;
    }

    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    public Boolean isVerified() {
        return verified;
    }

    public Uploads verified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getVerificationStatus() {
        return verificationStatus;
    }

    public Uploads verificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
        return this;
    }

    public void setVerificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Instant getSecurityLastChangedAt() {
        return securityLastChangedAt;
    }

    public Uploads securityLastChangedAt(Instant securityLastChangedAt) {
        this.securityLastChangedAt = securityLastChangedAt;
        return this;
    }

    public void setSecurityLastChangedAt(Instant securityLastChangedAt) {
        this.securityLastChangedAt = securityLastChangedAt;
    }

    public String getSecurityLastChangedReason() {
        return securityLastChangedReason;
    }

    public Uploads securityLastChangedReason(String securityLastChangedReason) {
        this.securityLastChangedReason = securityLastChangedReason;
        return this;
    }

    public void setSecurityLastChangedReason(String securityLastChangedReason) {
        this.securityLastChangedReason = securityLastChangedReason;
    }

    public UserProfiles getUserProfiles() {
        return userProfiles;
    }

    public Uploads userProfiles(UserProfiles userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    public void setUserProfiles(UserProfiles userProfiles) {
        this.userProfiles = userProfiles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uploads)) {
            return false;
        }
        return id != null && id.equals(((Uploads) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uploads{" +
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
            "}";
    }
}
