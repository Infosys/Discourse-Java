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
 * A DTO for the {@link com.infy.domain.UserProfiles} entity.
 */
public class UserProfilesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    private String location;

    private String website;

    private String bioRaw;

    private String bioCooked;

    private Integer dismissedBannerKey;

    private Integer bioCookedVersion;

    private Boolean badgeGrantedTitle;

    @NotNull
    private Integer views;

    private Long profileBackgroundUploadId;

    private Long cardBackgroundUploadId;

    private Long grantedTitleBadgeId;

    private Long featuredTopicId;


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBioRaw() {
        return bioRaw;
    }

    public void setBioRaw(String bioRaw) {
        this.bioRaw = bioRaw;
    }

    public String getBioCooked() {
        return bioCooked;
    }

    public void setBioCooked(String bioCooked) {
        this.bioCooked = bioCooked;
    }

    public Integer getDismissedBannerKey() {
        return dismissedBannerKey;
    }

    public void setDismissedBannerKey(Integer dismissedBannerKey) {
        this.dismissedBannerKey = dismissedBannerKey;
    }

    public Integer getBioCookedVersion() {
        return bioCookedVersion;
    }

    public void setBioCookedVersion(Integer bioCookedVersion) {
        this.bioCookedVersion = bioCookedVersion;
    }

    public Boolean isBadgeGrantedTitle() {
        return badgeGrantedTitle;
    }

    public void setBadgeGrantedTitle(Boolean badgeGrantedTitle) {
        this.badgeGrantedTitle = badgeGrantedTitle;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Long getProfileBackgroundUploadId() {
        return profileBackgroundUploadId;
    }

    public void setProfileBackgroundUploadId(Long profileBackgroundUploadId) {
        this.profileBackgroundUploadId = profileBackgroundUploadId;
    }

    public Long getCardBackgroundUploadId() {
        return cardBackgroundUploadId;
    }

    public void setCardBackgroundUploadId(Long cardBackgroundUploadId) {
        this.cardBackgroundUploadId = cardBackgroundUploadId;
    }

    public Long getGrantedTitleBadgeId() {
        return grantedTitleBadgeId;
    }

    public void setGrantedTitleBadgeId(Long grantedTitleBadgeId) {
        this.grantedTitleBadgeId = grantedTitleBadgeId;
    }

    public Long getFeaturedTopicId() {
        return featuredTopicId;
    }

    public void setFeaturedTopicId(Long featuredTopicId) {
        this.featuredTopicId = featuredTopicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfilesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserProfilesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfilesDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", location='" + getLocation() + "'" +
            ", website='" + getWebsite() + "'" +
            ", bioRaw='" + getBioRaw() + "'" +
            ", bioCooked='" + getBioCooked() + "'" +
            ", dismissedBannerKey=" + getDismissedBannerKey() +
            ", bioCookedVersion=" + getBioCookedVersion() +
            ", badgeGrantedTitle='" + isBadgeGrantedTitle() + "'" +
            ", views=" + getViews() +
            ", profileBackgroundUploadId=" + getProfileBackgroundUploadId() +
            ", cardBackgroundUploadId=" + getCardBackgroundUploadId() +
            ", grantedTitleBadgeId=" + getGrantedTitleBadgeId() +
            ", featuredTopicId=" + getFeaturedTopicId() +
            "}";
    }
}
