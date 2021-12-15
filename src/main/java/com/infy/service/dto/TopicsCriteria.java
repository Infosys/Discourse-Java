/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.infy.domain.Topics} entity. This class is used
 * in {@link com.infy.web.rest.TopicsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /topics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TopicsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private InstantFilter lastPostedAt;

    private IntegerFilter views;

    private IntegerFilter postsCount;

    private StringFilter userId;

    private StringFilter lastPostUserId;

    private IntegerFilter replyCount;

    private StringFilter featuredUser1Id;

    private StringFilter featuredUser2Id;

    private StringFilter featuredUser3Id;

    private InstantFilter deletedAt;

    private IntegerFilter highestPostNumber;

    private IntegerFilter likeCount;

    private IntegerFilter incomingLinkCount;

    private LongFilter categoryId;

    private BooleanFilter visible;

    private IntegerFilter moderatorPostsCount;

    private BooleanFilter closed;

    private BooleanFilter archived;

    private InstantFilter bumpedAt;

    private BooleanFilter hasSummary;

    private StringFilter archetype;

    private StringFilter featuredUser4Id;

    private IntegerFilter notifyModeratorsCount;

    private IntegerFilter spamCount;

    private InstantFilter pinnedAt;

    private DoubleFilter score;

    private DoubleFilter percentRank;

    private StringFilter subtype;

    private StringFilter slug;

    private StringFilter deletedById;

    private IntegerFilter participantCount;

    private IntegerFilter wordCount;

    private StringFilter excerpt;

    private BooleanFilter pinnedGlobally;

    private InstantFilter pinnedUntil;

    private StringFilter fancyTitle;

    private IntegerFilter highestStaffPostNumber;

    private StringFilter featuredLink;

    private DoubleFilter reviewableScore;

    private LongFilter imageUploadId;

    private IntegerFilter slowModeSeconds;

    public TopicsCriteria() {
    }

    public TopicsCriteria(TopicsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.lastPostedAt = other.lastPostedAt == null ? null : other.lastPostedAt.copy();
        this.views = other.views == null ? null : other.views.copy();
        this.postsCount = other.postsCount == null ? null : other.postsCount.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.lastPostUserId = other.lastPostUserId == null ? null : other.lastPostUserId.copy();
        this.replyCount = other.replyCount == null ? null : other.replyCount.copy();
        this.featuredUser1Id = other.featuredUser1Id == null ? null : other.featuredUser1Id.copy();
        this.featuredUser2Id = other.featuredUser2Id == null ? null : other.featuredUser2Id.copy();
        this.featuredUser3Id = other.featuredUser3Id == null ? null : other.featuredUser3Id.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.highestPostNumber = other.highestPostNumber == null ? null : other.highestPostNumber.copy();
        this.likeCount = other.likeCount == null ? null : other.likeCount.copy();
        this.incomingLinkCount = other.incomingLinkCount == null ? null : other.incomingLinkCount.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.visible = other.visible == null ? null : other.visible.copy();
        this.moderatorPostsCount = other.moderatorPostsCount == null ? null : other.moderatorPostsCount.copy();
        this.closed = other.closed == null ? null : other.closed.copy();
        this.archived = other.archived == null ? null : other.archived.copy();
        this.bumpedAt = other.bumpedAt == null ? null : other.bumpedAt.copy();
        this.hasSummary = other.hasSummary == null ? null : other.hasSummary.copy();
        this.archetype = other.archetype == null ? null : other.archetype.copy();
        this.featuredUser4Id = other.featuredUser4Id == null ? null : other.featuredUser4Id.copy();
        this.notifyModeratorsCount = other.notifyModeratorsCount == null ? null : other.notifyModeratorsCount.copy();
        this.spamCount = other.spamCount == null ? null : other.spamCount.copy();
        this.pinnedAt = other.pinnedAt == null ? null : other.pinnedAt.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.percentRank = other.percentRank == null ? null : other.percentRank.copy();
        this.subtype = other.subtype == null ? null : other.subtype.copy();
        this.slug = other.slug == null ? null : other.slug.copy();
        this.deletedById = other.deletedById == null ? null : other.deletedById.copy();
        this.participantCount = other.participantCount == null ? null : other.participantCount.copy();
        this.wordCount = other.wordCount == null ? null : other.wordCount.copy();
        this.excerpt = other.excerpt == null ? null : other.excerpt.copy();
        this.pinnedGlobally = other.pinnedGlobally == null ? null : other.pinnedGlobally.copy();
        this.pinnedUntil = other.pinnedUntil == null ? null : other.pinnedUntil.copy();
        this.fancyTitle = other.fancyTitle == null ? null : other.fancyTitle.copy();
        this.highestStaffPostNumber = other.highestStaffPostNumber == null ? null : other.highestStaffPostNumber.copy();
        this.featuredLink = other.featuredLink == null ? null : other.featuredLink.copy();
        this.reviewableScore = other.reviewableScore == null ? null : other.reviewableScore.copy();
        this.imageUploadId = other.imageUploadId == null ? null : other.imageUploadId.copy();
        this.slowModeSeconds = other.slowModeSeconds == null ? null : other.slowModeSeconds.copy();
    }

    @Override
    public TopicsCriteria copy() {
        return new TopicsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public InstantFilter getLastPostedAt() {
        return lastPostedAt;
    }

    public void setLastPostedAt(InstantFilter lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
    }

    public IntegerFilter getViews() {
        return views;
    }

    public void setViews(IntegerFilter views) {
        this.views = views;
    }

    public IntegerFilter getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(IntegerFilter postsCount) {
        this.postsCount = postsCount;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getLastPostUserId() {
        return lastPostUserId;
    }

    public void setLastPostUserId(StringFilter lastPostUserId) {
        this.lastPostUserId = lastPostUserId;
    }

    public IntegerFilter getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(IntegerFilter replyCount) {
        this.replyCount = replyCount;
    }

    public StringFilter getFeaturedUser1Id() {
        return featuredUser1Id;
    }

    public void setFeaturedUser1Id(StringFilter featuredUser1Id) {
        this.featuredUser1Id = featuredUser1Id;
    }

    public StringFilter getFeaturedUser2Id() {
        return featuredUser2Id;
    }

    public void setFeaturedUser2Id(StringFilter featuredUser2Id) {
        this.featuredUser2Id = featuredUser2Id;
    }

    public StringFilter getFeaturedUser3Id() {
        return featuredUser3Id;
    }

    public void setFeaturedUser3Id(StringFilter featuredUser3Id) {
        this.featuredUser3Id = featuredUser3Id;
    }

    public InstantFilter getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(InstantFilter deletedAt) {
        this.deletedAt = deletedAt;
    }

    public IntegerFilter getHighestPostNumber() {
        return highestPostNumber;
    }

    public void setHighestPostNumber(IntegerFilter highestPostNumber) {
        this.highestPostNumber = highestPostNumber;
    }

    public IntegerFilter getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(IntegerFilter likeCount) {
        this.likeCount = likeCount;
    }

    public IntegerFilter getIncomingLinkCount() {
        return incomingLinkCount;
    }

    public void setIncomingLinkCount(IntegerFilter incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public BooleanFilter getVisible() {
        return visible;
    }

    public void setVisible(BooleanFilter visible) {
        this.visible = visible;
    }

    public IntegerFilter getModeratorPostsCount() {
        return moderatorPostsCount;
    }

    public void setModeratorPostsCount(IntegerFilter moderatorPostsCount) {
        this.moderatorPostsCount = moderatorPostsCount;
    }

    public BooleanFilter getClosed() {
        return closed;
    }

    public void setClosed(BooleanFilter closed) {
        this.closed = closed;
    }

    public BooleanFilter getArchived() {
        return archived;
    }

    public void setArchived(BooleanFilter archived) {
        this.archived = archived;
    }

    public InstantFilter getBumpedAt() {
        return bumpedAt;
    }

    public void setBumpedAt(InstantFilter bumpedAt) {
        this.bumpedAt = bumpedAt;
    }

    public BooleanFilter getHasSummary() {
        return hasSummary;
    }

    public void setHasSummary(BooleanFilter hasSummary) {
        this.hasSummary = hasSummary;
    }

    public StringFilter getArchetype() {
        return archetype;
    }

    public void setArchetype(StringFilter archetype) {
        this.archetype = archetype;
    }

    public StringFilter getFeaturedUser4Id() {
        return featuredUser4Id;
    }

    public void setFeaturedUser4Id(StringFilter featuredUser4Id) {
        this.featuredUser4Id = featuredUser4Id;
    }

    public IntegerFilter getNotifyModeratorsCount() {
        return notifyModeratorsCount;
    }

    public void setNotifyModeratorsCount(IntegerFilter notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
    }

    public IntegerFilter getSpamCount() {
        return spamCount;
    }

    public void setSpamCount(IntegerFilter spamCount) {
        this.spamCount = spamCount;
    }

    public InstantFilter getPinnedAt() {
        return pinnedAt;
    }

    public void setPinnedAt(InstantFilter pinnedAt) {
        this.pinnedAt = pinnedAt;
    }

    public DoubleFilter getScore() {
        return score;
    }

    public void setScore(DoubleFilter score) {
        this.score = score;
    }

    public DoubleFilter getPercentRank() {
        return percentRank;
    }

    public void setPercentRank(DoubleFilter percentRank) {
        this.percentRank = percentRank;
    }

    public StringFilter getSubtype() {
        return subtype;
    }

    public void setSubtype(StringFilter subtype) {
        this.subtype = subtype;
    }

    public StringFilter getSlug() {
        return slug;
    }

    public void setSlug(StringFilter slug) {
        this.slug = slug;
    }

    public StringFilter getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(StringFilter deletedById) {
        this.deletedById = deletedById;
    }

    public IntegerFilter getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(IntegerFilter participantCount) {
        this.participantCount = participantCount;
    }

    public IntegerFilter getWordCount() {
        return wordCount;
    }

    public void setWordCount(IntegerFilter wordCount) {
        this.wordCount = wordCount;
    }

    public StringFilter getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(StringFilter excerpt) {
        this.excerpt = excerpt;
    }

    public BooleanFilter getPinnedGlobally() {
        return pinnedGlobally;
    }

    public void setPinnedGlobally(BooleanFilter pinnedGlobally) {
        this.pinnedGlobally = pinnedGlobally;
    }

    public InstantFilter getPinnedUntil() {
        return pinnedUntil;
    }

    public void setPinnedUntil(InstantFilter pinnedUntil) {
        this.pinnedUntil = pinnedUntil;
    }

    public StringFilter getFancyTitle() {
        return fancyTitle;
    }

    public void setFancyTitle(StringFilter fancyTitle) {
        this.fancyTitle = fancyTitle;
    }

    public IntegerFilter getHighestStaffPostNumber() {
        return highestStaffPostNumber;
    }

    public void setHighestStaffPostNumber(IntegerFilter highestStaffPostNumber) {
        this.highestStaffPostNumber = highestStaffPostNumber;
    }

    public StringFilter getFeaturedLink() {
        return featuredLink;
    }

    public void setFeaturedLink(StringFilter featuredLink) {
        this.featuredLink = featuredLink;
    }

    public DoubleFilter getReviewableScore() {
        return reviewableScore;
    }

    public void setReviewableScore(DoubleFilter reviewableScore) {
        this.reviewableScore = reviewableScore;
    }

    public LongFilter getImageUploadId() {
        return imageUploadId;
    }

    public void setImageUploadId(LongFilter imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    public IntegerFilter getSlowModeSeconds() {
        return slowModeSeconds;
    }

    public void setSlowModeSeconds(IntegerFilter slowModeSeconds) {
        this.slowModeSeconds = slowModeSeconds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TopicsCriteria that = (TopicsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(lastPostedAt, that.lastPostedAt) &&
            Objects.equals(views, that.views) &&
            Objects.equals(postsCount, that.postsCount) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(lastPostUserId, that.lastPostUserId) &&
            Objects.equals(replyCount, that.replyCount) &&
            Objects.equals(featuredUser1Id, that.featuredUser1Id) &&
            Objects.equals(featuredUser2Id, that.featuredUser2Id) &&
            Objects.equals(featuredUser3Id, that.featuredUser3Id) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(highestPostNumber, that.highestPostNumber) &&
            Objects.equals(likeCount, that.likeCount) &&
            Objects.equals(incomingLinkCount, that.incomingLinkCount) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(visible, that.visible) &&
            Objects.equals(moderatorPostsCount, that.moderatorPostsCount) &&
            Objects.equals(closed, that.closed) &&
            Objects.equals(archived, that.archived) &&
            Objects.equals(bumpedAt, that.bumpedAt) &&
            Objects.equals(hasSummary, that.hasSummary) &&
            Objects.equals(archetype, that.archetype) &&
            Objects.equals(featuredUser4Id, that.featuredUser4Id) &&
            Objects.equals(notifyModeratorsCount, that.notifyModeratorsCount) &&
            Objects.equals(spamCount, that.spamCount) &&
            Objects.equals(pinnedAt, that.pinnedAt) &&
            Objects.equals(score, that.score) &&
            Objects.equals(percentRank, that.percentRank) &&
            Objects.equals(subtype, that.subtype) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(deletedById, that.deletedById) &&
            Objects.equals(participantCount, that.participantCount) &&
            Objects.equals(wordCount, that.wordCount) &&
            Objects.equals(excerpt, that.excerpt) &&
            Objects.equals(pinnedGlobally, that.pinnedGlobally) &&
            Objects.equals(pinnedUntil, that.pinnedUntil) &&
            Objects.equals(fancyTitle, that.fancyTitle) &&
            Objects.equals(highestStaffPostNumber, that.highestStaffPostNumber) &&
            Objects.equals(featuredLink, that.featuredLink) &&
            Objects.equals(reviewableScore, that.reviewableScore) &&
            Objects.equals(imageUploadId, that.imageUploadId) &&
            Objects.equals(slowModeSeconds, that.slowModeSeconds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        lastPostedAt,
        views,
        postsCount,
        userId,
        lastPostUserId,
        replyCount,
        featuredUser1Id,
        featuredUser2Id,
        featuredUser3Id,
        deletedAt,
        highestPostNumber,
        likeCount,
        incomingLinkCount,
        categoryId,
        visible,
        moderatorPostsCount,
        closed,
        archived,
        bumpedAt,
        hasSummary,
        archetype,
        featuredUser4Id,
        notifyModeratorsCount,
        spamCount,
        pinnedAt,
        score,
        percentRank,
        subtype,
        slug,
        deletedById,
        participantCount,
        wordCount,
        excerpt,
        pinnedGlobally,
        pinnedUntil,
        fancyTitle,
        highestStaffPostNumber,
        featuredLink,
        reviewableScore,
        imageUploadId,
        slowModeSeconds
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (lastPostedAt != null ? "lastPostedAt=" + lastPostedAt + ", " : "") +
                (views != null ? "views=" + views + ", " : "") +
                (postsCount != null ? "postsCount=" + postsCount + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (lastPostUserId != null ? "lastPostUserId=" + lastPostUserId + ", " : "") +
                (replyCount != null ? "replyCount=" + replyCount + ", " : "") +
                (featuredUser1Id != null ? "featuredUser1Id=" + featuredUser1Id + ", " : "") +
                (featuredUser2Id != null ? "featuredUser2Id=" + featuredUser2Id + ", " : "") +
                (featuredUser3Id != null ? "featuredUser3Id=" + featuredUser3Id + ", " : "") +
                (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
                (highestPostNumber != null ? "highestPostNumber=" + highestPostNumber + ", " : "") +
                (likeCount != null ? "likeCount=" + likeCount + ", " : "") +
                (incomingLinkCount != null ? "incomingLinkCount=" + incomingLinkCount + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (visible != null ? "visible=" + visible + ", " : "") +
                (moderatorPostsCount != null ? "moderatorPostsCount=" + moderatorPostsCount + ", " : "") +
                (closed != null ? "closed=" + closed + ", " : "") +
                (archived != null ? "archived=" + archived + ", " : "") +
                (bumpedAt != null ? "bumpedAt=" + bumpedAt + ", " : "") +
                (hasSummary != null ? "hasSummary=" + hasSummary + ", " : "") +
                (archetype != null ? "archetype=" + archetype + ", " : "") +
                (featuredUser4Id != null ? "featuredUser4Id=" + featuredUser4Id + ", " : "") +
                (notifyModeratorsCount != null ? "notifyModeratorsCount=" + notifyModeratorsCount + ", " : "") +
                (spamCount != null ? "spamCount=" + spamCount + ", " : "") +
                (pinnedAt != null ? "pinnedAt=" + pinnedAt + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (percentRank != null ? "percentRank=" + percentRank + ", " : "") +
                (subtype != null ? "subtype=" + subtype + ", " : "") +
                (slug != null ? "slug=" + slug + ", " : "") +
                (deletedById != null ? "deletedById=" + deletedById + ", " : "") +
                (participantCount != null ? "participantCount=" + participantCount + ", " : "") +
                (wordCount != null ? "wordCount=" + wordCount + ", " : "") +
                (excerpt != null ? "excerpt=" + excerpt + ", " : "") +
                (pinnedGlobally != null ? "pinnedGlobally=" + pinnedGlobally + ", " : "") +
                (pinnedUntil != null ? "pinnedUntil=" + pinnedUntil + ", " : "") +
                (fancyTitle != null ? "fancyTitle=" + fancyTitle + ", " : "") +
                (highestStaffPostNumber != null ? "highestStaffPostNumber=" + highestStaffPostNumber + ", " : "") +
                (featuredLink != null ? "featuredLink=" + featuredLink + ", " : "") +
                (reviewableScore != null ? "reviewableScore=" + reviewableScore + ", " : "") +
                (imageUploadId != null ? "imageUploadId=" + imageUploadId + ", " : "") +
                (slowModeSeconds != null ? "slowModeSeconds=" + slowModeSeconds + ", " : "") +
            "}";
    }

}
