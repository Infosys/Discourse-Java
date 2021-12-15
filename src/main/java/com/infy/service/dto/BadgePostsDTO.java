/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.infy.domain.BadgePosts} entity.
 */
public class BadgePostsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String userId;

    private Long topicId;

    private Long postNumber;

    @Lob
    private String raw;

    private String cooked;

    private Long replyToPostNumber;

    private Integer replyCount;

    private Integer quoteCount;

    private Instant deletedAt;

    private Integer offTopicCount;

    private Integer likeCount;

    private Integer incomingLinkCount;

    private Integer bookmarkCount;

    private Double score;

    private Integer reads;

    private Integer postType;

    private Integer sortOrder;

    private String lastEditorId;

    private Boolean hidden;

    private Long hiddenReasonId;

    private Integer notifyModeratorsCount;

    private Integer spamCount;

    private Integer illegalCount;

    private Integer inappropriateCount;

    private Instant lastVersionAt;

    private Boolean userDeleted;

    private String replyToUserId;

    private Double percentRank;

    private Integer notifyUserCount;

    private Integer likeScore;

    private String deletedById;

    private String editReason;

    private Integer wordCount;

    private Integer version;

    private Integer cookMethod;

    private Boolean wiki;

    private Instant bakedAt;

    private Integer bakedVersion;

    private Instant hiddenAt;

    private Integer selfEdits;

    private Boolean replyQuoted;

    private Boolean viaEmail;

    private String rawEmail;

    private Integer publicVersion;

    private String actionCode;

    private String lockedById;

    private Long imageUploadId;


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

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Long postNumber) {
        this.postNumber = postNumber;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getCooked() {
        return cooked;
    }

    public void setCooked(String cooked) {
        this.cooked = cooked;
    }

    public Long getReplyToPostNumber() {
        return replyToPostNumber;
    }

    public void setReplyToPostNumber(Long replyToPostNumber) {
        this.replyToPostNumber = replyToPostNumber;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getOffTopicCount() {
        return offTopicCount;
    }

    public void setOffTopicCount(Integer offTopicCount) {
        this.offTopicCount = offTopicCount;
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

    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getReads() {
        return reads;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getLastEditorId() {
        return lastEditorId;
    }

    public void setLastEditorId(String lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Long getHiddenReasonId() {
        return hiddenReasonId;
    }

    public void setHiddenReasonId(Long hiddenReasonId) {
        this.hiddenReasonId = hiddenReasonId;
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

    public Integer getIllegalCount() {
        return illegalCount;
    }

    public void setIllegalCount(Integer illegalCount) {
        this.illegalCount = illegalCount;
    }

    public Integer getInappropriateCount() {
        return inappropriateCount;
    }

    public void setInappropriateCount(Integer inappropriateCount) {
        this.inappropriateCount = inappropriateCount;
    }

    public Instant getLastVersionAt() {
        return lastVersionAt;
    }

    public void setLastVersionAt(Instant lastVersionAt) {
        this.lastVersionAt = lastVersionAt;
    }

    public Boolean isUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
    }

    public String getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public Double getPercentRank() {
        return percentRank;
    }

    public void setPercentRank(Double percentRank) {
        this.percentRank = percentRank;
    }

    public Integer getNotifyUserCount() {
        return notifyUserCount;
    }

    public void setNotifyUserCount(Integer notifyUserCount) {
        this.notifyUserCount = notifyUserCount;
    }

    public Integer getLikeScore() {
        return likeScore;
    }

    public void setLikeScore(Integer likeScore) {
        this.likeScore = likeScore;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public String getEditReason() {
        return editReason;
    }

    public void setEditReason(String editReason) {
        this.editReason = editReason;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCookMethod() {
        return cookMethod;
    }

    public void setCookMethod(Integer cookMethod) {
        this.cookMethod = cookMethod;
    }

    public Boolean isWiki() {
        return wiki;
    }

    public void setWiki(Boolean wiki) {
        this.wiki = wiki;
    }

    public Instant getBakedAt() {
        return bakedAt;
    }

    public void setBakedAt(Instant bakedAt) {
        this.bakedAt = bakedAt;
    }

    public Integer getBakedVersion() {
        return bakedVersion;
    }

    public void setBakedVersion(Integer bakedVersion) {
        this.bakedVersion = bakedVersion;
    }

    public Instant getHiddenAt() {
        return hiddenAt;
    }

    public void setHiddenAt(Instant hiddenAt) {
        this.hiddenAt = hiddenAt;
    }

    public Integer getSelfEdits() {
        return selfEdits;
    }

    public void setSelfEdits(Integer selfEdits) {
        this.selfEdits = selfEdits;
    }

    public Boolean isReplyQuoted() {
        return replyQuoted;
    }

    public void setReplyQuoted(Boolean replyQuoted) {
        this.replyQuoted = replyQuoted;
    }

    public Boolean isViaEmail() {
        return viaEmail;
    }

    public void setViaEmail(Boolean viaEmail) {
        this.viaEmail = viaEmail;
    }

    public String getRawEmail() {
        return rawEmail;
    }

    public void setRawEmail(String rawEmail) {
        this.rawEmail = rawEmail;
    }

    public Integer getPublicVersion() {
        return publicVersion;
    }

    public void setPublicVersion(Integer publicVersion) {
        this.publicVersion = publicVersion;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getLockedById() {
        return lockedById;
    }

    public void setLockedById(String lockedById) {
        this.lockedById = lockedById;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgePostsDTO)) {
            return false;
        }

        return id != null && id.equals(((BadgePostsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgePostsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", topicId=" + getTopicId() +
            ", postNumber=" + getPostNumber() +
            ", raw='" + getRaw() + "'" +
            ", cooked='" + getCooked() + "'" +
            ", replyToPostNumber=" + getReplyToPostNumber() +
            ", replyCount=" + getReplyCount() +
            ", quoteCount=" + getQuoteCount() +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", offTopicCount=" + getOffTopicCount() +
            ", likeCount=" + getLikeCount() +
            ", incomingLinkCount=" + getIncomingLinkCount() +
            ", bookmarkCount=" + getBookmarkCount() +
            ", score=" + getScore() +
            ", reads=" + getReads() +
            ", postType=" + getPostType() +
            ", sortOrder=" + getSortOrder() +
            ", lastEditorId='" + getLastEditorId() + "'" +
            ", hidden='" + isHidden() + "'" +
            ", hiddenReasonId=" + getHiddenReasonId() +
            ", notifyModeratorsCount=" + getNotifyModeratorsCount() +
            ", spamCount=" + getSpamCount() +
            ", illegalCount=" + getIllegalCount() +
            ", inappropriateCount=" + getInappropriateCount() +
            ", lastVersionAt='" + getLastVersionAt() + "'" +
            ", userDeleted='" + isUserDeleted() + "'" +
            ", replyToUserId='" + getReplyToUserId() + "'" +
            ", percentRank=" + getPercentRank() +
            ", notifyUserCount=" + getNotifyUserCount() +
            ", likeScore=" + getLikeScore() +
            ", deletedById='" + getDeletedById() + "'" +
            ", editReason='" + getEditReason() + "'" +
            ", wordCount=" + getWordCount() +
            ", version=" + getVersion() +
            ", cookMethod=" + getCookMethod() +
            ", wiki='" + isWiki() + "'" +
            ", bakedAt='" + getBakedAt() + "'" +
            ", bakedVersion=" + getBakedVersion() +
            ", hiddenAt='" + getHiddenAt() + "'" +
            ", selfEdits=" + getSelfEdits() +
            ", replyQuoted='" + isReplyQuoted() + "'" +
            ", viaEmail='" + isViaEmail() + "'" +
            ", rawEmail='" + getRawEmail() + "'" +
            ", publicVersion=" + getPublicVersion() +
            ", actionCode='" + getActionCode() + "'" +
            ", lockedById='" + getLockedById() + "'" +
            ", imageUploadId=" + getImageUploadId() +
            "}";
    }
}
