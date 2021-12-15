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

/**
 * A Badges.
 */
@Entity
@Table(name = "badges")
public class Badges extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "badge_type_id", nullable = false)
    private Long badgeTypeId;

    @NotNull
    @Column(name = "grant_count", nullable = false)
    private Integer grantCount;

    @NotNull
    @Column(name = "allow_title", nullable = false)
    private Boolean allowTitle;

    @NotNull
    @Column(name = "multiple_grant", nullable = false)
    private Boolean multipleGrant;

    @Column(name = "icon")
    private String icon;

    @Column(name = "listable")
    private Boolean listable;

    @Column(name = "target_posts")
    private Boolean targetPosts;

    @Column(name = "query")
    private String query;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "auto_revoke", nullable = false)
    private Boolean autoRevoke;

    @NotNull
    @Column(name = "badge_grouping_id", nullable = false)
    private Long badgeGroupingId;

    @Column(name = "trigger")
    private Integer trigger;

    @NotNull
    @Column(name = "show_posts", nullable = false)
    private Boolean showPosts;

    @NotNull
    @Column(name = "system", nullable = false)
    private Boolean system;

    @Column(name = "image")
    private String image;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "image_upload_id")
    private Long imageUploadId;

    @ManyToOne
    @JsonIgnoreProperties(value = "badges", allowSetters = true)
    private UserProfiles userProfiles;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Badges name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Badges description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBadgeTypeId() {
        return badgeTypeId;
    }

    public Badges badgeTypeId(Long badgeTypeId) {
        this.badgeTypeId = badgeTypeId;
        return this;
    }

    public void setBadgeTypeId(Long badgeTypeId) {
        this.badgeTypeId = badgeTypeId;
    }

    public Integer getGrantCount() {
        return grantCount;
    }

    public Badges grantCount(Integer grantCount) {
        this.grantCount = grantCount;
        return this;
    }

    public void setGrantCount(Integer grantCount) {
        this.grantCount = grantCount;
    }

    public Boolean isAllowTitle() {
        return allowTitle;
    }

    public Badges allowTitle(Boolean allowTitle) {
        this.allowTitle = allowTitle;
        return this;
    }

    public void setAllowTitle(Boolean allowTitle) {
        this.allowTitle = allowTitle;
    }

    public Boolean isMultipleGrant() {
        return multipleGrant;
    }

    public Badges multipleGrant(Boolean multipleGrant) {
        this.multipleGrant = multipleGrant;
        return this;
    }

    public void setMultipleGrant(Boolean multipleGrant) {
        this.multipleGrant = multipleGrant;
    }

    public String getIcon() {
        return icon;
    }

    public Badges icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean isListable() {
        return listable;
    }

    public Badges listable(Boolean listable) {
        this.listable = listable;
        return this;
    }

    public void setListable(Boolean listable) {
        this.listable = listable;
    }

    public Boolean isTargetPosts() {
        return targetPosts;
    }

    public Badges targetPosts(Boolean targetPosts) {
        this.targetPosts = targetPosts;
        return this;
    }

    public void setTargetPosts(Boolean targetPosts) {
        this.targetPosts = targetPosts;
    }

    public String getQuery() {
        return query;
    }

    public Badges query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Badges enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isAutoRevoke() {
        return autoRevoke;
    }

    public Badges autoRevoke(Boolean autoRevoke) {
        this.autoRevoke = autoRevoke;
        return this;
    }

    public void setAutoRevoke(Boolean autoRevoke) {
        this.autoRevoke = autoRevoke;
    }

    public Long getBadgeGroupingId() {
        return badgeGroupingId;
    }

    public Badges badgeGroupingId(Long badgeGroupingId) {
        this.badgeGroupingId = badgeGroupingId;
        return this;
    }

    public void setBadgeGroupingId(Long badgeGroupingId) {
        this.badgeGroupingId = badgeGroupingId;
    }

    public Integer getTrigger() {
        return trigger;
    }

    public Badges trigger(Integer trigger) {
        this.trigger = trigger;
        return this;
    }

    public void setTrigger(Integer trigger) {
        this.trigger = trigger;
    }

    public Boolean isShowPosts() {
        return showPosts;
    }

    public Badges showPosts(Boolean showPosts) {
        this.showPosts = showPosts;
        return this;
    }

    public void setShowPosts(Boolean showPosts) {
        this.showPosts = showPosts;
    }

    public Boolean isSystem() {
        return system;
    }

    public Badges system(Boolean system) {
        this.system = system;
        return this;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getImage() {
        return image;
    }

    public Badges image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Badges longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public Badges imageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
        return this;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    public UserProfiles getUserProfiles() {
        return userProfiles;
    }

    public Badges userProfiles(UserProfiles userProfiles) {
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
        if (!(o instanceof Badges)) {
            return false;
        }
        return id != null && id.equals(((Badges) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Badges{" +
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
            "}";
    }
}
