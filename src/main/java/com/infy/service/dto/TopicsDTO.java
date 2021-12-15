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
 * A DTO for the {@link com.infy.domain.Topics} entity.
 */
public class TopicsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private Instant lastPostedAt;

    @NotNull
    private Integer views;

    @NotNull
    private Integer postsCount;

    private String userId;

    @NotNull
    private String lastPostUserId;

    @NotNull
    private Integer replyCount;

    private String featuredUser1Id;

    private String featuredUser2Id;

    private String featuredUser3Id;

    private Instant deletedAt;

    @NotNull
    private Integer highestPostNumber;

    @NotNull
    private Integer likeCount;

    @NotNull
    private Integer incomingLinkCount;

    private Long categoryId;

    @NotNull
    private Boolean visible;

    @NotNull
    private Integer moderatorPostsCount;

    @NotNull
    private Boolean closed;

    @NotNull
    private Boolean archived;

    @NotNull
    private Instant bumpedAt;

    @NotNull
    private Boolean hasSummary;

    @NotNull
    private String archetype;

    private String featuredUser4Id;

    @NotNull
    private Integer notifyModeratorsCount;

    @NotNull
    private Integer spamCount;

    private Instant pinnedAt;

    private Double score;

    @NotNull
    private Double percentRank;

    private String subtype;

    private String slug;

    private String deletedById;

    private Integer participantCount;

    private Integer wordCount;

    private String excerpt;

    @NotNull
    private Boolean pinnedGlobally;

    private Instant pinnedUntil;

    private String fancyTitle;

    @NotNull
    private Integer highestStaffPostNumber;

    private String featuredLink;

    @NotNull
    private Double reviewableScore;

    private Long imageUploadId;

    @NotNull
    private Integer slowModeSeconds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getLastPostedAt() {
        return lastPostedAt;
    }

    public void setLastPostedAt(Instant lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastPostUserId() {
        return lastPostUserId;
    }

    public void setLastPostUserId(String lastPostUserId) {
        this.lastPostUserId = lastPostUserId;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public String getFeaturedUser1Id() {
        return featuredUser1Id;
    }

    public void setFeaturedUser1Id(String featuredUser1Id) {
        this.featuredUser1Id = featuredUser1Id;
    }

    public String getFeaturedUser2Id() {
        return featuredUser2Id;
    }

    public void setFeaturedUser2Id(String featuredUser2Id) {
        this.featuredUser2Id = featuredUser2Id;
    }

    public String getFeaturedUser3Id() {
        return featuredUser3Id;
    }

    public void setFeaturedUser3Id(String featuredUser3Id) {
        this.featuredUser3Id = featuredUser3Id;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getHighestPostNumber() {
        return highestPostNumber;
    }

    public void setHighestPostNumber(Integer highestPostNumber) {
        this.highestPostNumber = highestPostNumber;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getIncomingLinkCount() {
        return incomingLinkCount;
    }

    public void setIncomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getModeratorPostsCount() {
        return moderatorPostsCount;
    }

    public void setModeratorPostsCount(Integer moderatorPostsCount) {
        this.moderatorPostsCount = moderatorPostsCount;
    }

    public Boolean isClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Instant getBumpedAt() {
        return bumpedAt;
    }

    public void setBumpedAt(Instant bumpedAt) {
        this.bumpedAt = bumpedAt;
    }

    public Boolean isHasSummary() {
        return hasSummary;
    }

    public void setHasSummary(Boolean hasSummary) {
        this.hasSummary = hasSummary;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getFeaturedUser4Id() {
        return featuredUser4Id;
    }

    public void setFeaturedUser4Id(String featuredUser4Id) {
        this.featuredUser4Id = featuredUser4Id;
    }

    public Integer getNotifyModeratorsCount() {
        return notifyModeratorsCount;
    }

    public void setNotifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
    }

    public Integer getSpamCount() {
        return spamCount;
    }

    public void setSpamCount(Integer spamCount) {
        this.spamCount = spamCount;
    }

    public Instant getPinnedAt() {
        return pinnedAt;
    }

    public void setPinnedAt(Instant pinnedAt) {
        this.pinnedAt = pinnedAt;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getPercentRank() {
        return percentRank;
    }

    public void setPercentRank(Double percentRank) {
        this.percentRank = percentRank;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public Boolean isPinnedGlobally() {
        return pinnedGlobally;
    }

    public void setPinnedGlobally(Boolean pinnedGlobally) {
        this.pinnedGlobally = pinnedGlobally;
    }

    public Instant getPinnedUntil() {
        return pinnedUntil;
    }

    public void setPinnedUntil(Instant pinnedUntil) {
        this.pinnedUntil = pinnedUntil;
    }

    public String getFancyTitle() {
        return fancyTitle;
    }

    public void setFancyTitle(String fancyTitle) {
        this.fancyTitle = fancyTitle;
    }

    public Integer getHighestStaffPostNumber() {
        return highestStaffPostNumber;
    }

    public void setHighestStaffPostNumber(Integer highestStaffPostNumber) {
        this.highestStaffPostNumber = highestStaffPostNumber;
    }

    public String getFeaturedLink() {
        return featuredLink;
    }

    public void setFeaturedLink(String featuredLink) {
        this.featuredLink = featuredLink;
    }

    public Double getReviewableScore() {
        return reviewableScore;
    }

    public void setReviewableScore(Double reviewableScore) {
        this.reviewableScore = reviewableScore;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    public Integer getSlowModeSeconds() {
        return slowModeSeconds;
    }

    public void setSlowModeSeconds(Integer slowModeSeconds) {
        this.slowModeSeconds = slowModeSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicsDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", lastPostedAt='" + getLastPostedAt() + "'" +
            ", views=" + getViews() +
            ", postsCount=" + getPostsCount() +
            ", userId='" + getUserId() + "'" +
            ", lastPostUserId=" + getLastPostUserId() +
            ", replyCount=" + getReplyCount() +
            ", featuredUser1Id='" + getFeaturedUser1Id() + "'" +
            ", featuredUser2Id='" + getFeaturedUser2Id() + "'" +
            ", featuredUser3Id='" + getFeaturedUser3Id() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", highestPostNumber=" + getHighestPostNumber() +
            ", likeCount=" + getLikeCount() +
            ", incomingLinkCount=" + getIncomingLinkCount() +
            ", categoryId=" + getCategoryId() +
            ", visible='" + isVisible() + "'" +
            ", moderatorPostsCount=" + getModeratorPostsCount() +
            ", closed='" + isClosed() + "'" +
            ", archived='" + isArchived() + "'" +
            ", bumpedAt='" + getBumpedAt() + "'" +
            ", hasSummary='" + isHasSummary() + "'" +
            ", archetype='" + getArchetype() + "'" +
            ", featuredUser4Id='" + getFeaturedUser4Id() + "'" +
            ", notifyModeratorsCount=" + getNotifyModeratorsCount() +
            ", spamCount=" + getSpamCount() +
            ", pinnedAt='" + getPinnedAt() + "'" +
            ", score=" + getScore() +
            ", percentRank=" + getPercentRank() +
            ", subtype='" + getSubtype() + "'" +
            ", slug='" + getSlug() + "'" +
            ", deletedById='" + getDeletedById() + "'" +
            ", participantCount=" + getParticipantCount() +
            ", wordCount=" + getWordCount() +
            ", excerpt='" + getExcerpt() + "'" +
            ", pinnedGlobally='" + isPinnedGlobally() + "'" +
            ", pinnedUntil='" + getPinnedUntil() + "'" +
            ", fancyTitle='" + getFancyTitle() + "'" +
            ", highestStaffPostNumber=" + getHighestStaffPostNumber() +
            ", featuredLink='" + getFeaturedLink() + "'" +
            ", reviewableScore=" + getReviewableScore() +
            ", imageUploadId=" + getImageUploadId() +
            ", slowModeSeconds=" + getSlowModeSeconds() +
            "}";
    }
}
