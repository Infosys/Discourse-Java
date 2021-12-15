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
import java.util.HashSet;
import java.util.Set;

/**
 * A UserProfiles.
 */
@Entity
@Table(name = "user_profiles")
public class UserProfiles extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "location")
    private String location;

    @Column(name = "website")
    private String website;

    @Column(name = "bio_raw")
    private String bioRaw;

    @Column(name = "bio_cooked")
    private String bioCooked;

    @Column(name = "dismissed_banner_key")
    private Integer dismissedBannerKey;

    @Column(name = "bio_cooked_version")
    private Integer bioCookedVersion;

    @Column(name = "badge_granted_title")
    private Boolean badgeGrantedTitle;

    @NotNull
    @Column(name = "views", nullable = false)
    private Integer views;

    @Column(name = "profile_background_upload_id")
    private Long profileBackgroundUploadId;

    @Column(name = "card_background_upload_id")
    private Long cardBackgroundUploadId;

    @Column(name = "granted_title_badge_id")
    private Long grantedTitleBadgeId;

    @Column(name = "featured_topic_id")
    private Long featuredTopicId;

    @OneToMany(mappedBy = "userProfiles")
    private Set<Badges> badges = new HashSet<>();

    @OneToMany(mappedBy = "userProfiles")
    private Set<Uploads> uploads = new HashSet<>();

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

    public UserProfiles userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public UserProfiles location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public UserProfiles website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBioRaw() {
        return bioRaw;
    }

    public UserProfiles bioRaw(String bioRaw) {
        this.bioRaw = bioRaw;
        return this;
    }

    public void setBioRaw(String bioRaw) {
        this.bioRaw = bioRaw;
    }

    public String getBioCooked() {
        return bioCooked;
    }

    public UserProfiles bioCooked(String bioCooked) {
        this.bioCooked = bioCooked;
        return this;
    }

    public void setBioCooked(String bioCooked) {
        this.bioCooked = bioCooked;
    }

    public Integer getDismissedBannerKey() {
        return dismissedBannerKey;
    }

    public UserProfiles dismissedBannerKey(Integer dismissedBannerKey) {
        this.dismissedBannerKey = dismissedBannerKey;
        return this;
    }

    public void setDismissedBannerKey(Integer dismissedBannerKey) {
        this.dismissedBannerKey = dismissedBannerKey;
    }

    public Integer getBioCookedVersion() {
        return bioCookedVersion;
    }

    public UserProfiles bioCookedVersion(Integer bioCookedVersion) {
        this.bioCookedVersion = bioCookedVersion;
        return this;
    }

    public void setBioCookedVersion(Integer bioCookedVersion) {
        this.bioCookedVersion = bioCookedVersion;
    }

    public Boolean isBadgeGrantedTitle() {
        return badgeGrantedTitle;
    }

    public UserProfiles badgeGrantedTitle(Boolean badgeGrantedTitle) {
        this.badgeGrantedTitle = badgeGrantedTitle;
        return this;
    }

    public void setBadgeGrantedTitle(Boolean badgeGrantedTitle) {
        this.badgeGrantedTitle = badgeGrantedTitle;
    }

    public Integer getViews() {
        return views;
    }

    public UserProfiles views(Integer views) {
        this.views = views;
        return this;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Long getProfileBackgroundUploadId() {
        return profileBackgroundUploadId;
    }

    public UserProfiles profileBackgroundUploadId(Long profileBackgroundUploadId) {
        this.profileBackgroundUploadId = profileBackgroundUploadId;
        return this;
    }

    public void setProfileBackgroundUploadId(Long profileBackgroundUploadId) {
        this.profileBackgroundUploadId = profileBackgroundUploadId;
    }

    public Long getCardBackgroundUploadId() {
        return cardBackgroundUploadId;
    }

    public UserProfiles cardBackgroundUploadId(Long cardBackgroundUploadId) {
        this.cardBackgroundUploadId = cardBackgroundUploadId;
        return this;
    }

    public void setCardBackgroundUploadId(Long cardBackgroundUploadId) {
        this.cardBackgroundUploadId = cardBackgroundUploadId;
    }

    public Long getGrantedTitleBadgeId() {
        return grantedTitleBadgeId;
    }

    public UserProfiles grantedTitleBadgeId(Long grantedTitleBadgeId) {
        this.grantedTitleBadgeId = grantedTitleBadgeId;
        return this;
    }

    public void setGrantedTitleBadgeId(Long grantedTitleBadgeId) {
        this.grantedTitleBadgeId = grantedTitleBadgeId;
    }

    public Long getFeaturedTopicId() {
        return featuredTopicId;
    }

    public UserProfiles featuredTopicId(Long featuredTopicId) {
        this.featuredTopicId = featuredTopicId;
        return this;
    }

    public void setFeaturedTopicId(Long featuredTopicId) {
        this.featuredTopicId = featuredTopicId;
    }

    public Set<Badges> getBadges() {
        return badges;
    }

    public UserProfiles badges(Set<Badges> badges) {
        this.badges = badges;
        return this;
    }

    public UserProfiles addBadges(Badges badges) {
        this.badges.add(badges);
        badges.setUserProfiles(this);
        return this;
    }

    public UserProfiles removeBadges(Badges badges) {
        this.badges.remove(badges);
        badges.setUserProfiles(null);
        return this;
    }

    public void setBadges(Set<Badges> badges) {
        this.badges = badges;
    }

    public Set<Uploads> getUploads() {
        return uploads;
    }

    public UserProfiles uploads(Set<Uploads> uploads) {
        this.uploads = uploads;
        return this;
    }

    public UserProfiles addUploads(Uploads uploads) {
        this.uploads.add(uploads);
        uploads.setUserProfiles(this);
        return this;
    }

    public UserProfiles removeUploads(Uploads uploads) {
        this.uploads.remove(uploads);
        uploads.setUserProfiles(null);
        return this;
    }

    public void setUploads(Set<Uploads> uploads) {
        this.uploads = uploads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfiles)) {
            return false;
        }
        return id != null && id.equals(((UserProfiles) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfiles{" +
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
