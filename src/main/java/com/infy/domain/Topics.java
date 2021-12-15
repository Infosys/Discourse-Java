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
import java.time.Instant;

/**
 * A Topics.
 */
@Entity
@Table(name = "topics")
public class Topics extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "last_posted_at")
    private Instant lastPostedAt;

    @NotNull
    @Column(name = "views", nullable = false)
    private Integer views;

    @NotNull
    @Column(name = "posts_count", nullable = false)
    private Integer postsCount;

    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "last_post_user_id", nullable = false)
    private String lastPostUserId;

    @NotNull
    @Column(name = "reply_count", nullable = false)
    private Integer replyCount;

    @Column(name = "featured_user_1_id")
    private String featuredUser1Id;

    @Column(name = "featured_user_2_id")
    private String featuredUser2Id;

    @Column(name = "featured_user_3_id")
    private String featuredUser3Id;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "highest_post_number", nullable = false)
    private Integer highestPostNumber;

    @NotNull
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @NotNull
    @Column(name = "incoming_link_count", nullable = false)
    private Integer incomingLinkCount;

    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "visible", nullable = false)
    private Boolean visible;

    @NotNull
    @Column(name = "moderator_posts_count", nullable = false)
    private Integer moderatorPostsCount;

    @NotNull
    @Column(name = "closed", nullable = false)
    private Boolean closed;

    @NotNull
    @Column(name = "archived", nullable = false)
    private Boolean archived;

    @NotNull
    @Column(name = "bumped_at", nullable = false)
    private Instant bumpedAt;

    @NotNull
    @Column(name = "has_summary", nullable = false)
    private Boolean hasSummary;

    @NotNull
    @Column(name = "archetype", nullable = false)
    private String archetype;

    @Column(name = "featured_user_4_id")
    private String featuredUser4Id;

    @NotNull
    @Column(name = "notify_moderators_count", nullable = false)
    private Integer notifyModeratorsCount;

    @NotNull
    @Column(name = "spam_count", nullable = false)
    private Integer spamCount;

    @Column(name = "pinned_at")
    private Instant pinnedAt;

    @Column(name = "score")
    private Double score;

    @NotNull
    @Column(name = "percent_rank", nullable = false)
    private Double percentRank;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "slug")
    private String slug;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "participant_count")
    private Integer participantCount;

    @Column(name = "word_count")
    private Integer wordCount;

    @Column(name = "excerpt")
    private String excerpt;

    @NotNull
    @Column(name = "pinned_globally", nullable = false)
    private Boolean pinnedGlobally;

    @Column(name = "pinned_until")
    private Instant pinnedUntil;

    @Column(name = "fancy_title")
    private String fancyTitle;

    @NotNull
    @Column(name = "highest_staff_post_number", nullable = false)
    private Integer highestStaffPostNumber;

    @Column(name = "featured_link")
    private String featuredLink;

    @NotNull
    @Column(name = "reviewable_score", nullable = false)
    private Double reviewableScore;

    @Column(name = "image_upload_id")
    private Long imageUploadId;

    @NotNull
    @Column(name = "slow_mode_seconds", nullable = false)
    private Integer slowModeSeconds;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Topics title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getLastPostedAt() {
        return lastPostedAt;
    }

    public Topics lastPostedAt(Instant lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
        return this;
    }

    public void setLastPostedAt(Instant lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
    }

    public Integer getViews() {
        return views;
    }

    public Topics views(Integer views) {
        this.views = views;
        return this;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public Topics postsCount(Integer postsCount) {
        this.postsCount = postsCount;
        return this;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public String getUserId() {
        return userId;
    }

    public Topics userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastPostUserId() {
        return lastPostUserId;
    }

    public Topics lastPostUserId(String lastPostUserId) {
        this.lastPostUserId = lastPostUserId;
        return this;
    }

    public void setLastPostUserId(String lastPostUserId) {
        this.lastPostUserId = lastPostUserId;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public Topics replyCount(Integer replyCount) {
        this.replyCount = replyCount;
        return this;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public String getFeaturedUser1Id() {
        return featuredUser1Id;
    }

    public Topics featuredUser1Id(String featuredUser1Id) {
        this.featuredUser1Id = featuredUser1Id;
        return this;
    }

    public void setFeaturedUser1Id(String featuredUser1Id) {
        this.featuredUser1Id = featuredUser1Id;
    }

    public String getFeaturedUser2Id() {
        return featuredUser2Id;
    }

    public Topics featuredUser2Id(String featuredUser2Id) {
        this.featuredUser2Id = featuredUser2Id;
        return this;
    }

    public void setFeaturedUser2Id(String featuredUser2Id) {
        this.featuredUser2Id = featuredUser2Id;
    }

    public String getFeaturedUser3Id() {
        return featuredUser3Id;
    }

    public Topics featuredUser3Id(String featuredUser3Id) {
        this.featuredUser3Id = featuredUser3Id;
        return this;
    }

    public void setFeaturedUser3Id(String featuredUser3Id) {
        this.featuredUser3Id = featuredUser3Id;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Topics deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getHighestPostNumber() {
        return highestPostNumber;
    }

    public Topics highestPostNumber(Integer highestPostNumber) {
        this.highestPostNumber = highestPostNumber;
        return this;
    }

    public void setHighestPostNumber(Integer highestPostNumber) {
        this.highestPostNumber = highestPostNumber;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public Topics likeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getIncomingLinkCount() {
        return incomingLinkCount;
    }

    public Topics incomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
        return this;
    }

    public void setIncomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Topics categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Topics visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getModeratorPostsCount() {
        return moderatorPostsCount;
    }

    public Topics moderatorPostsCount(Integer moderatorPostsCount) {
        this.moderatorPostsCount = moderatorPostsCount;
        return this;
    }

    public void setModeratorPostsCount(Integer moderatorPostsCount) {
        this.moderatorPostsCount = moderatorPostsCount;
    }

    public Boolean isClosed() {
        return closed;
    }

    public Topics closed(Boolean closed) {
        this.closed = closed;
        return this;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public Topics archived(Boolean archived) {
        this.archived = archived;
        return this;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Instant getBumpedAt() {
        return bumpedAt;
    }

    public Topics bumpedAt(Instant bumpedAt) {
        this.bumpedAt = bumpedAt;
        return this;
    }

    public void setBumpedAt(Instant bumpedAt) {
        this.bumpedAt = bumpedAt;
    }

    public Boolean isHasSummary() {
        return hasSummary;
    }

    public Topics hasSummary(Boolean hasSummary) {
        this.hasSummary = hasSummary;
        return this;
    }

    public void setHasSummary(Boolean hasSummary) {
        this.hasSummary = hasSummary;
    }

    public String getArchetype() {
        return archetype;
    }

    public Topics archetype(String archetype) {
        this.archetype = archetype;
        return this;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getFeaturedUser4Id() {
        return featuredUser4Id;
    }

    public Topics featuredUser4Id(String featuredUser4Id) {
        this.featuredUser4Id = featuredUser4Id;
        return this;
    }

    public void setFeaturedUser4Id(String featuredUser4Id) {
        this.featuredUser4Id = featuredUser4Id;
    }

    public Integer getNotifyModeratorsCount() {
        return notifyModeratorsCount;
    }

    public Topics notifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
        return this;
    }

    public void setNotifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
    }

    public Integer getSpamCount() {
        return spamCount;
    }

    public Topics spamCount(Integer spamCount) {
        this.spamCount = spamCount;
        return this;
    }

    public void setSpamCount(Integer spamCount) {
        this.spamCount = spamCount;
    }

    public Instant getPinnedAt() {
        return pinnedAt;
    }

    public Topics pinnedAt(Instant pinnedAt) {
        this.pinnedAt = pinnedAt;
        return this;
    }

    public void setPinnedAt(Instant pinnedAt) {
        this.pinnedAt = pinnedAt;
    }

    public Double getScore() {
        return score;
    }

    public Topics score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getPercentRank() {
        return percentRank;
    }

    public Topics percentRank(Double percentRank) {
        this.percentRank = percentRank;
        return this;
    }

    public void setPercentRank(Double percentRank) {
        this.percentRank = percentRank;
    }

    public String getSubtype() {
        return subtype;
    }

    public Topics subtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSlug() {
        return slug;
    }

    public Topics slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public Topics deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public Topics participantCount(Integer participantCount) {
        this.participantCount = participantCount;
        return this;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public Topics wordCount(Integer wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public Topics excerpt(String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public Boolean isPinnedGlobally() {
        return pinnedGlobally;
    }

    public Topics pinnedGlobally(Boolean pinnedGlobally) {
        this.pinnedGlobally = pinnedGlobally;
        return this;
    }

    public void setPinnedGlobally(Boolean pinnedGlobally) {
        this.pinnedGlobally = pinnedGlobally;
    }

    public Instant getPinnedUntil() {
        return pinnedUntil;
    }

    public Topics pinnedUntil(Instant pinnedUntil) {
        this.pinnedUntil = pinnedUntil;
        return this;
    }

    public void setPinnedUntil(Instant pinnedUntil) {
        this.pinnedUntil = pinnedUntil;
    }

    public String getFancyTitle() {
        return fancyTitle;
    }

    public Topics fancyTitle(String fancyTitle) {
        this.fancyTitle = fancyTitle;
        return this;
    }

    public void setFancyTitle(String fancyTitle) {
        this.fancyTitle = fancyTitle;
    }

    public Integer getHighestStaffPostNumber() {
        return highestStaffPostNumber;
    }

    public Topics highestStaffPostNumber(Integer highestStaffPostNumber) {
        this.highestStaffPostNumber = highestStaffPostNumber;
        return this;
    }

    public void setHighestStaffPostNumber(Integer highestStaffPostNumber) {
        this.highestStaffPostNumber = highestStaffPostNumber;
    }

    public String getFeaturedLink() {
        return featuredLink;
    }

    public Topics featuredLink(String featuredLink) {
        this.featuredLink = featuredLink;
        return this;
    }

    public void setFeaturedLink(String featuredLink) {
        this.featuredLink = featuredLink;
    }

    public Double getReviewableScore() {
        return reviewableScore;
    }

    public Topics reviewableScore(Double reviewableScore) {
        this.reviewableScore = reviewableScore;
        return this;
    }

    public void setReviewableScore(Double reviewableScore) {
        this.reviewableScore = reviewableScore;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public Topics imageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
        return this;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    public Integer getSlowModeSeconds() {
        return slowModeSeconds;
    }

    public Topics slowModeSeconds(Integer slowModeSeconds) {
        this.slowModeSeconds = slowModeSeconds;
        return this;
    }

    public void setSlowModeSeconds(Integer slowModeSeconds) {
        this.slowModeSeconds = slowModeSeconds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topics)) {
            return false;
        }
        return id != null && id.equals(((Topics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Topics{" +
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
