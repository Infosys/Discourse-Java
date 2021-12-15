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
 * A DTO for the {@link com.infy.domain.Badges} entity.
 */
public class BadgesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Long badgeTypeId;

    @NotNull
    private Integer grantCount;

    @NotNull
    private Boolean allowTitle;

    @NotNull
    private Boolean multipleGrant;

    private String icon;

    private Boolean listable;

    private Boolean targetPosts;

    private String query;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean autoRevoke;

    @NotNull
    private Long badgeGroupingId;

    private Integer trigger;

    @NotNull
    private Boolean showPosts;

    @NotNull
    private Boolean system;

    private String image;

    private String longDescription;

    private Long imageUploadId;


    private Long userProfilesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBadgeTypeId() {
        return badgeTypeId;
    }

    public void setBadgeTypeId(Long badgeTypeId) {
        this.badgeTypeId = badgeTypeId;
    }

    public Integer getGrantCount() {
        return grantCount;
    }

    public void setGrantCount(Integer grantCount) {
        this.grantCount = grantCount;
    }

    public Boolean isAllowTitle() {
        return allowTitle;
    }

    public void setAllowTitle(Boolean allowTitle) {
        this.allowTitle = allowTitle;
    }

    public Boolean isMultipleGrant() {
        return multipleGrant;
    }

    public void setMultipleGrant(Boolean multipleGrant) {
        this.multipleGrant = multipleGrant;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean isListable() {
        return listable;
    }

    public void setListable(Boolean listable) {
        this.listable = listable;
    }

    public Boolean isTargetPosts() {
        return targetPosts;
    }

    public void setTargetPosts(Boolean targetPosts) {
        this.targetPosts = targetPosts;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isAutoRevoke() {
        return autoRevoke;
    }

    public void setAutoRevoke(Boolean autoRevoke) {
        this.autoRevoke = autoRevoke;
    }

    public Long getBadgeGroupingId() {
        return badgeGroupingId;
    }

    public void setBadgeGroupingId(Long badgeGroupingId) {
        this.badgeGroupingId = badgeGroupingId;
    }

    public Integer getTrigger() {
        return trigger;
    }

    public void setTrigger(Integer trigger) {
        this.trigger = trigger;
    }

    public Boolean isShowPosts() {
        return showPosts;
    }

    public void setShowPosts(Boolean showPosts) {
        this.showPosts = showPosts;
    }

    public Boolean isSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
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
        if (!(o instanceof BadgesDTO)) {
            return false;
        }

        return id != null && id.equals(((BadgesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", badgeTypeId=" + getBadgeTypeId() +
            ", grantCount=" + getGrantCount() +
            ", allowTitle='" + isAllowTitle() + "'" +
            ", multipleGrant='" + isMultipleGrant() + "'" +
            ", icon='" + getIcon() + "'" +
            ", listable='" + isListable() + "'" +
            ", targetPosts='" + isTargetPosts() + "'" +
            ", query='" + getQuery() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", autoRevoke='" + isAutoRevoke() + "'" +
            ", badgeGroupingId=" + getBadgeGroupingId() +
            ", trigger=" + getTrigger() +
            ", showPosts='" + isShowPosts() + "'" +
            ", system='" + isSystem() + "'" +
            ", image='" + getImage() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", imageUploadId=" + getImageUploadId() +
            ", userProfilesId=" + getUserProfilesId() +
            "}";
    }
}
