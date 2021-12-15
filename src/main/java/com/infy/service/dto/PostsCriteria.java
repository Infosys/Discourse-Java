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
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.infy.domain.Posts} entity. This class is used
 * in {@link com.infy.web.rest.PostsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userId;

    private LongFilter topicId;

    private IntegerFilter postNumber;

    private StringFilter cooked;

    private LongFilter replyToPostNumber;

    private IntegerFilter replyCount;

    private IntegerFilter quoteCount;

    private InstantFilter deletedAt;

    private IntegerFilter offTopicCount;

    private IntegerFilter likeCount;

    private IntegerFilter incomingLinkCount;

    private IntegerFilter bookmarkCount;

    private DoubleFilter score;

    private IntegerFilter reads;

    private IntegerFilter postType;

    private IntegerFilter sortOrder;

    private StringFilter lastEditorId;

    private BooleanFilter hidden;

    private LongFilter hiddenReasonId;

    private IntegerFilter notifyModeratorsCount;

    private IntegerFilter spamCount;

    private IntegerFilter illegalCount;

    private IntegerFilter inappropriateCount;

    private InstantFilter lastVersionAt;

    private BooleanFilter userDeleted;

    private StringFilter replyToUserId;

    private DoubleFilter percentRank;

    private IntegerFilter notifyUserCount;

    private IntegerFilter likeScore;

    private StringFilter deletedById;

    private StringFilter editReason;

    private IntegerFilter wordCount;

    private IntegerFilter version;

    private IntegerFilter cookMethod;

    private BooleanFilter wiki;

    private InstantFilter bakedAt;

    private IntegerFilter bakedVersion;

    private InstantFilter hiddenAt;

    private IntegerFilter selfEdits;

    private BooleanFilter replyQuoted;

    private BooleanFilter viaEmail;

    private StringFilter rawEmail;

    private IntegerFilter publicVersion;

    private StringFilter actionCode;

    private StringFilter lockedById;

    private LongFilter imageUploadId;

    private LongFilter pollsId;

    public PostsCriteria() {
    }

    public PostsCriteria(PostsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.topicId = other.topicId == null ? null : other.topicId.copy();
        this.postNumber = other.postNumber == null ? null : other.postNumber.copy();
        this.cooked = other.cooked == null ? null : other.cooked.copy();
        this.replyToPostNumber = other.replyToPostNumber == null ? null : other.replyToPostNumber.copy();
        this.replyCount = other.replyCount == null ? null : other.replyCount.copy();
        this.quoteCount = other.quoteCount == null ? null : other.quoteCount.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.offTopicCount = other.offTopicCount == null ? null : other.offTopicCount.copy();
        this.likeCount = other.likeCount == null ? null : other.likeCount.copy();
        this.incomingLinkCount = other.incomingLinkCount == null ? null : other.incomingLinkCount.copy();
        this.bookmarkCount = other.bookmarkCount == null ? null : other.bookmarkCount.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.reads = other.reads == null ? null : other.reads.copy();
        this.postType = other.postType == null ? null : other.postType.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.lastEditorId = other.lastEditorId == null ? null : other.lastEditorId.copy();
        this.hidden = other.hidden == null ? null : other.hidden.copy();
        this.hiddenReasonId = other.hiddenReasonId == null ? null : other.hiddenReasonId.copy();
        this.notifyModeratorsCount = other.notifyModeratorsCount == null ? null : other.notifyModeratorsCount.copy();
        this.spamCount = other.spamCount == null ? null : other.spamCount.copy();
        this.illegalCount = other.illegalCount == null ? null : other.illegalCount.copy();
        this.inappropriateCount = other.inappropriateCount == null ? null : other.inappropriateCount.copy();
        this.lastVersionAt = other.lastVersionAt == null ? null : other.lastVersionAt.copy();
        this.userDeleted = other.userDeleted == null ? null : other.userDeleted.copy();
        this.replyToUserId = other.replyToUserId == null ? null : other.replyToUserId.copy();
        this.percentRank = other.percentRank == null ? null : other.percentRank.copy();
        this.notifyUserCount = other.notifyUserCount == null ? null : other.notifyUserCount.copy();
        this.likeScore = other.likeScore == null ? null : other.likeScore.copy();
        this.deletedById = other.deletedById == null ? null : other.deletedById.copy();
        this.editReason = other.editReason == null ? null : other.editReason.copy();
        this.wordCount = other.wordCount == null ? null : other.wordCount.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.cookMethod = other.cookMethod == null ? null : other.cookMethod.copy();
        this.wiki = other.wiki == null ? null : other.wiki.copy();
        this.bakedAt = other.bakedAt == null ? null : other.bakedAt.copy();
        this.bakedVersion = other.bakedVersion == null ? null : other.bakedVersion.copy();
        this.hiddenAt = other.hiddenAt == null ? null : other.hiddenAt.copy();
        this.selfEdits = other.selfEdits == null ? null : other.selfEdits.copy();
        this.replyQuoted = other.replyQuoted == null ? null : other.replyQuoted.copy();
        this.viaEmail = other.viaEmail == null ? null : other.viaEmail.copy();
        this.rawEmail = other.rawEmail == null ? null : other.rawEmail.copy();
        this.publicVersion = other.publicVersion == null ? null : other.publicVersion.copy();
        this.actionCode = other.actionCode == null ? null : other.actionCode.copy();
        this.lockedById = other.lockedById == null ? null : other.lockedById.copy();
        this.imageUploadId = other.imageUploadId == null ? null : other.imageUploadId.copy();
        this.pollsId = other.pollsId == null ? null : other.pollsId.copy();
    }

    @Override
    public PostsCriteria copy() {
        return new PostsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public LongFilter getTopicId() {
        return topicId;
    }

    public void setTopicId(LongFilter topicId) {
        this.topicId = topicId;
    }

    public IntegerFilter getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(IntegerFilter postNumber) {
        this.postNumber = postNumber;
    }

    public StringFilter getCooked() {
        return cooked;
    }

    public void setCooked(StringFilter cooked) {
        this.cooked = cooked;
    }

    public LongFilter getReplyToPostNumber() {
        return replyToPostNumber;
    }

    public void setReplyToPostNumber(LongFilter replyToPostNumber) {
        this.replyToPostNumber = replyToPostNumber;
    }

    public IntegerFilter getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(IntegerFilter replyCount) {
        this.replyCount = replyCount;
    }

    public IntegerFilter getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(IntegerFilter quoteCount) {
        this.quoteCount = quoteCount;
    }

    public InstantFilter getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(InstantFilter deletedAt) {
        this.deletedAt = deletedAt;
    }

    public IntegerFilter getOffTopicCount() {
        return offTopicCount;
    }

    public void setOffTopicCount(IntegerFilter offTopicCount) {
        this.offTopicCount = offTopicCount;
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

    public IntegerFilter getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(IntegerFilter bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public DoubleFilter getScore() {
        return score;
    }

    public void setScore(DoubleFilter score) {
        this.score = score;
    }

    public IntegerFilter getReads() {
        return reads;
    }

    public void setReads(IntegerFilter reads) {
        this.reads = reads;
    }

    public IntegerFilter getPostType() {
        return postType;
    }

    public void setPostType(IntegerFilter postType) {
        this.postType = postType;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public StringFilter getLastEditorId() {
        return lastEditorId;
    }

    public void setLastEditorId(StringFilter lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public BooleanFilter getHidden() {
        return hidden;
    }

    public void setHidden(BooleanFilter hidden) {
        this.hidden = hidden;
    }

    public LongFilter getHiddenReasonId() {
        return hiddenReasonId;
    }

    public void setHiddenReasonId(LongFilter hiddenReasonId) {
        this.hiddenReasonId = hiddenReasonId;
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

    public IntegerFilter getIllegalCount() {
        return illegalCount;
    }

    public void setIllegalCount(IntegerFilter illegalCount) {
        this.illegalCount = illegalCount;
    }

    public IntegerFilter getInappropriateCount() {
        return inappropriateCount;
    }

    public void setInappropriateCount(IntegerFilter inappropriateCount) {
        this.inappropriateCount = inappropriateCount;
    }

    public InstantFilter getLastVersionAt() {
        return lastVersionAt;
    }

    public void setLastVersionAt(InstantFilter lastVersionAt) {
        this.lastVersionAt = lastVersionAt;
    }

    public BooleanFilter getUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(BooleanFilter userDeleted) {
        this.userDeleted = userDeleted;
    }

    public StringFilter getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(StringFilter replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public DoubleFilter getPercentRank() {
        return percentRank;
    }

    public void setPercentRank(DoubleFilter percentRank) {
        this.percentRank = percentRank;
    }

    public IntegerFilter getNotifyUserCount() {
        return notifyUserCount;
    }

    public void setNotifyUserCount(IntegerFilter notifyUserCount) {
        this.notifyUserCount = notifyUserCount;
    }

    public IntegerFilter getLikeScore() {
        return likeScore;
    }

    public void setLikeScore(IntegerFilter likeScore) {
        this.likeScore = likeScore;
    }

    public StringFilter getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(StringFilter deletedById) {
        this.deletedById = deletedById;
    }

    public StringFilter getEditReason() {
        return editReason;
    }

    public void setEditReason(StringFilter editReason) {
        this.editReason = editReason;
    }

    public IntegerFilter getWordCount() {
        return wordCount;
    }

    public void setWordCount(IntegerFilter wordCount) {
        this.wordCount = wordCount;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public IntegerFilter getCookMethod() {
        return cookMethod;
    }

    public void setCookMethod(IntegerFilter cookMethod) {
        this.cookMethod = cookMethod;
    }

    public BooleanFilter getWiki() {
        return wiki;
    }

    public void setWiki(BooleanFilter wiki) {
        this.wiki = wiki;
    }

    public InstantFilter getBakedAt() {
        return bakedAt;
    }

    public void setBakedAt(InstantFilter bakedAt) {
        this.bakedAt = bakedAt;
    }

    public IntegerFilter getBakedVersion() {
        return bakedVersion;
    }

    public void setBakedVersion(IntegerFilter bakedVersion) {
        this.bakedVersion = bakedVersion;
    }

    public InstantFilter getHiddenAt() {
        return hiddenAt;
    }

    public void setHiddenAt(InstantFilter hiddenAt) {
        this.hiddenAt = hiddenAt;
    }

    public IntegerFilter getSelfEdits() {
        return selfEdits;
    }

    public void setSelfEdits(IntegerFilter selfEdits) {
        this.selfEdits = selfEdits;
    }

    public BooleanFilter getReplyQuoted() {
        return replyQuoted;
    }

    public void setReplyQuoted(BooleanFilter replyQuoted) {
        this.replyQuoted = replyQuoted;
    }

    public BooleanFilter getViaEmail() {
        return viaEmail;
    }

    public void setViaEmail(BooleanFilter viaEmail) {
        this.viaEmail = viaEmail;
    }

    public StringFilter getRawEmail() {
        return rawEmail;
    }

    public void setRawEmail(StringFilter rawEmail) {
        this.rawEmail = rawEmail;
    }

    public IntegerFilter getPublicVersion() {
        return publicVersion;
    }

    public void setPublicVersion(IntegerFilter publicVersion) {
        this.publicVersion = publicVersion;
    }

    public StringFilter getActionCode() {
        return actionCode;
    }

    public void setActionCode(StringFilter actionCode) {
        this.actionCode = actionCode;
    }

    public StringFilter getLockedById() {
        return lockedById;
    }

    public void setLockedById(StringFilter lockedById) {
        this.lockedById = lockedById;
    }

    public LongFilter getImageUploadId() {
        return imageUploadId;
    }

    public void setImageUploadId(LongFilter imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    public LongFilter getPollsId() {
        return pollsId;
    }

    public void setPollsId(LongFilter pollsId) {
        this.pollsId = pollsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostsCriteria that = (PostsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(topicId, that.topicId) &&
            Objects.equals(postNumber, that.postNumber) &&
            Objects.equals(cooked, that.cooked) &&
            Objects.equals(replyToPostNumber, that.replyToPostNumber) &&
            Objects.equals(replyCount, that.replyCount) &&
            Objects.equals(quoteCount, that.quoteCount) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(offTopicCount, that.offTopicCount) &&
            Objects.equals(likeCount, that.likeCount) &&
            Objects.equals(incomingLinkCount, that.incomingLinkCount) &&
            Objects.equals(bookmarkCount, that.bookmarkCount) &&
            Objects.equals(score, that.score) &&
            Objects.equals(reads, that.reads) &&
            Objects.equals(postType, that.postType) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(lastEditorId, that.lastEditorId) &&
            Objects.equals(hidden, that.hidden) &&
            Objects.equals(hiddenReasonId, that.hiddenReasonId) &&
            Objects.equals(notifyModeratorsCount, that.notifyModeratorsCount) &&
            Objects.equals(spamCount, that.spamCount) &&
            Objects.equals(illegalCount, that.illegalCount) &&
            Objects.equals(inappropriateCount, that.inappropriateCount) &&
            Objects.equals(lastVersionAt, that.lastVersionAt) &&
            Objects.equals(userDeleted, that.userDeleted) &&
            Objects.equals(replyToUserId, that.replyToUserId) &&
            Objects.equals(percentRank, that.percentRank) &&
            Objects.equals(notifyUserCount, that.notifyUserCount) &&
            Objects.equals(likeScore, that.likeScore) &&
            Objects.equals(deletedById, that.deletedById) &&
            Objects.equals(editReason, that.editReason) &&
            Objects.equals(wordCount, that.wordCount) &&
            Objects.equals(version, that.version) &&
            Objects.equals(cookMethod, that.cookMethod) &&
            Objects.equals(wiki, that.wiki) &&
            Objects.equals(bakedAt, that.bakedAt) &&
            Objects.equals(bakedVersion, that.bakedVersion) &&
            Objects.equals(hiddenAt, that.hiddenAt) &&
            Objects.equals(selfEdits, that.selfEdits) &&
            Objects.equals(replyQuoted, that.replyQuoted) &&
            Objects.equals(viaEmail, that.viaEmail) &&
            Objects.equals(rawEmail, that.rawEmail) &&
            Objects.equals(publicVersion, that.publicVersion) &&
            Objects.equals(actionCode, that.actionCode) &&
            Objects.equals(lockedById, that.lockedById) &&
            Objects.equals(imageUploadId, that.imageUploadId) &&
            Objects.equals(pollsId, that.pollsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        topicId,
        postNumber,
        cooked,
        replyToPostNumber,
        replyCount,
        quoteCount,
        deletedAt,
        offTopicCount,
        likeCount,
        incomingLinkCount,
        bookmarkCount,
        score,
        reads,
        postType,
        sortOrder,
        lastEditorId,
        hidden,
        hiddenReasonId,
        notifyModeratorsCount,
        spamCount,
        illegalCount,
        inappropriateCount,
        lastVersionAt,
        userDeleted,
        replyToUserId,
        percentRank,
        notifyUserCount,
        likeScore,
        deletedById,
        editReason,
        wordCount,
        version,
        cookMethod,
        wiki,
        bakedAt,
        bakedVersion,
        hiddenAt,
        selfEdits,
        replyQuoted,
        viaEmail,
        rawEmail,
        publicVersion,
        actionCode,
        lockedById,
        imageUploadId,
        pollsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (topicId != null ? "topicId=" + topicId + ", " : "") +
                (postNumber != null ? "postNumber=" + postNumber + ", " : "") +
                (cooked != null ? "cooked=" + cooked + ", " : "") +
                (replyToPostNumber != null ? "replyToPostNumber=" + replyToPostNumber + ", " : "") +
                (replyCount != null ? "replyCount=" + replyCount + ", " : "") +
                (quoteCount != null ? "quoteCount=" + quoteCount + ", " : "") +
                (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
                (offTopicCount != null ? "offTopicCount=" + offTopicCount + ", " : "") +
                (likeCount != null ? "likeCount=" + likeCount + ", " : "") +
                (incomingLinkCount != null ? "incomingLinkCount=" + incomingLinkCount + ", " : "") +
                (bookmarkCount != null ? "bookmarkCount=" + bookmarkCount + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (reads != null ? "reads=" + reads + ", " : "") +
                (postType != null ? "postType=" + postType + ", " : "") +
                (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
                (lastEditorId != null ? "lastEditorId=" + lastEditorId + ", " : "") +
                (hidden != null ? "hidden=" + hidden + ", " : "") +
                (hiddenReasonId != null ? "hiddenReasonId=" + hiddenReasonId + ", " : "") +
                (notifyModeratorsCount != null ? "notifyModeratorsCount=" + notifyModeratorsCount + ", " : "") +
                (spamCount != null ? "spamCount=" + spamCount + ", " : "") +
                (illegalCount != null ? "illegalCount=" + illegalCount + ", " : "") +
                (inappropriateCount != null ? "inappropriateCount=" + inappropriateCount + ", " : "") +
                (lastVersionAt != null ? "lastVersionAt=" + lastVersionAt + ", " : "") +
                (userDeleted != null ? "userDeleted=" + userDeleted + ", " : "") +
                (replyToUserId != null ? "replyToUserId=" + replyToUserId + ", " : "") +
                (percentRank != null ? "percentRank=" + percentRank + ", " : "") +
                (notifyUserCount != null ? "notifyUserCount=" + notifyUserCount + ", " : "") +
                (likeScore != null ? "likeScore=" + likeScore + ", " : "") +
                (deletedById != null ? "deletedById=" + deletedById + ", " : "") +
                (editReason != null ? "editReason=" + editReason + ", " : "") +
                (wordCount != null ? "wordCount=" + wordCount + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (cookMethod != null ? "cookMethod=" + cookMethod + ", " : "") +
                (wiki != null ? "wiki=" + wiki + ", " : "") +
                (bakedAt != null ? "bakedAt=" + bakedAt + ", " : "") +
                (bakedVersion != null ? "bakedVersion=" + bakedVersion + ", " : "") +
                (hiddenAt != null ? "hiddenAt=" + hiddenAt + ", " : "") +
                (selfEdits != null ? "selfEdits=" + selfEdits + ", " : "") +
                (replyQuoted != null ? "replyQuoted=" + replyQuoted + ", " : "") +
                (viaEmail != null ? "viaEmail=" + viaEmail + ", " : "") +
                (rawEmail != null ? "rawEmail=" + rawEmail + ", " : "") +
                (publicVersion != null ? "publicVersion=" + publicVersion + ", " : "") +
                (actionCode != null ? "actionCode=" + actionCode + ", " : "") +
                (lockedById != null ? "lockedById=" + lockedById + ", " : "") +
                (imageUploadId != null ? "imageUploadId=" + imageUploadId + ", " : "") +
                (pollsId != null ? "pollsId=" + pollsId + ", " : "") +
            "}";
    }

}
