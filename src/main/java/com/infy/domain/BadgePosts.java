/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BadgePosts.
 */
@Entity
@Table(name = "badge_posts")
public class BadgePosts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "post_number")
    private Long postNumber;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "raw")
    private String raw;

    @Column(name = "cooked")
    private String cooked;

    @Column(name = "reply_to_post_number")
    private Long replyToPostNumber;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "quote_count")
    private Integer quoteCount;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "off_topic_count")
    private Integer offTopicCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "incoming_link_count")
    private Integer incomingLinkCount;

    @Column(name = "bookmark_count")
    private Integer bookmarkCount;

    @Column(name = "score")
    private Double score;

    @Column(name = "reads")
    private Integer reads;

    @Column(name = "post_type")
    private Integer postType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "last_editor_id")
    private String lastEditorId;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "hidden_reason_id")
    private Long hiddenReasonId;

    @Column(name = "notify_moderators_count")
    private Integer notifyModeratorsCount;

    @Column(name = "spam_count")
    private Integer spamCount;

    @Column(name = "illegal_count")
    private Integer illegalCount;

    @Column(name = "inappropriate_count")
    private Integer inappropriateCount;

    @Column(name = "last_version_at")
    private Instant lastVersionAt;

    @Column(name = "user_deleted")
    private Boolean userDeleted;

    @Column(name = "reply_to_user_id")
    private String replyToUserId;

    @Column(name = "percent_rank")
    private Double percentRank;

    @Column(name = "notify_user_count")
    private Integer notifyUserCount;

    @Column(name = "like_score")
    private Integer likeScore;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "edit_reason")
    private String editReason;

    @Column(name = "word_count")
    private Integer wordCount;

    @Column(name = "version")
    private Integer version;

    @Column(name = "cook_method")
    private Integer cookMethod;

    @Column(name = "wiki")
    private Boolean wiki;

    @Column(name = "baked_at")
    private Instant bakedAt;

    @Column(name = "baked_version")
    private Integer bakedVersion;

    @Column(name = "hidden_at")
    private Instant hiddenAt;

    @Column(name = "self_edits")
    private Integer selfEdits;

    @Column(name = "reply_quoted")
    private Boolean replyQuoted;

    @Column(name = "via_email")
    private Boolean viaEmail;

    @Column(name = "raw_email")
    private String rawEmail;

    @Column(name = "public_version")
    private Integer publicVersion;

    @Column(name = "action_code")
    private String actionCode;

    @Column(name = "locked_by_id")
    private String lockedById;

    @Column(name = "image_upload_id")
    private Long imageUploadId;

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

    public BadgePosts userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public BadgePosts topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostNumber() {
        return postNumber;
    }

    public BadgePosts postNumber(Long postNumber) {
        this.postNumber = postNumber;
        return this;
    }

    public void setPostNumber(Long postNumber) {
        this.postNumber = postNumber;
    }

    public String getRaw() {
        return raw;
    }

    public BadgePosts raw(String raw) {
        this.raw = raw;
        return this;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getCooked() {
        return cooked;
    }

    public BadgePosts cooked(String cooked) {
        this.cooked = cooked;
        return this;
    }

    public void setCooked(String cooked) {
        this.cooked = cooked;
    }

    public Long getReplyToPostNumber() {
        return replyToPostNumber;
    }

    public BadgePosts replyToPostNumber(Long replyToPostNumber) {
        this.replyToPostNumber = replyToPostNumber;
        return this;
    }

    public void setReplyToPostNumber(Long replyToPostNumber) {
        this.replyToPostNumber = replyToPostNumber;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public BadgePosts replyCount(Integer replyCount) {
        this.replyCount = replyCount;
        return this;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getQuoteCount() {
        return quoteCount;
    }

    public BadgePosts quoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
        return this;
    }

    public void setQuoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public BadgePosts deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getOffTopicCount() {
        return offTopicCount;
    }

    public BadgePosts offTopicCount(Integer offTopicCount) {
        this.offTopicCount = offTopicCount;
        return this;
    }

    public void setOffTopicCount(Integer offTopicCount) {
        this.offTopicCount = offTopicCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public BadgePosts likeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getIncomingLinkCount() {
        return incomingLinkCount;
    }

    public BadgePosts incomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
        return this;
    }

    public void setIncomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
    }

    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    public BadgePosts bookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
        return this;
    }

    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public Double getScore() {
        return score;
    }

    public BadgePosts score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getReads() {
        return reads;
    }

    public BadgePosts reads(Integer reads) {
        this.reads = reads;
        return this;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public Integer getPostType() {
        return postType;
    }

    public BadgePosts postType(Integer postType) {
        this.postType = postType;
        return this;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public BadgePosts sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getLastEditorId() {
        return lastEditorId;
    }

    public BadgePosts lastEditorId(String lastEditorId) {
        this.lastEditorId = lastEditorId;
        return this;
    }

    public void setLastEditorId(String lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public BadgePosts hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Long getHiddenReasonId() {
        return hiddenReasonId;
    }

    public BadgePosts hiddenReasonId(Long hiddenReasonId) {
        this.hiddenReasonId = hiddenReasonId;
        return this;
    }

    public void setHiddenReasonId(Long hiddenReasonId) {
        this.hiddenReasonId = hiddenReasonId;
    }

    public Integer getNotifyModeratorsCount() {
        return notifyModeratorsCount;
    }

    public BadgePosts notifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
        return this;
    }

    public void setNotifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
    }

    public Integer getSpamCount() {
        return spamCount;
    }

    public BadgePosts spamCount(Integer spamCount) {
        this.spamCount = spamCount;
        return this;
    }

    public void setSpamCount(Integer spamCount) {
        this.spamCount = spamCount;
    }

    public Integer getIllegalCount() {
        return illegalCount;
    }

    public BadgePosts illegalCount(Integer illegalCount) {
        this.illegalCount = illegalCount;
        return this;
    }

    public void setIllegalCount(Integer illegalCount) {
        this.illegalCount = illegalCount;
    }

    public Integer getInappropriateCount() {
        return inappropriateCount;
    }

    public BadgePosts inappropriateCount(Integer inappropriateCount) {
        this.inappropriateCount = inappropriateCount;
        return this;
    }

    public void setInappropriateCount(Integer inappropriateCount) {
        this.inappropriateCount = inappropriateCount;
    }

    public Instant getLastVersionAt() {
        return lastVersionAt;
    }

    public BadgePosts lastVersionAt(Instant lastVersionAt) {
        this.lastVersionAt = lastVersionAt;
        return this;
    }

    public void setLastVersionAt(Instant lastVersionAt) {
        this.lastVersionAt = lastVersionAt;
    }

    public Boolean isUserDeleted() {
        return userDeleted;
    }

    public BadgePosts userDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
    }

    public String getReplyToUserId() {
        return replyToUserId;
    }

    public BadgePosts replyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
        return this;
    }

    public void setReplyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public Double getPercentRank() {
        return percentRank;
    }

    public BadgePosts percentRank(Double percentRank) {
        this.percentRank = percentRank;
        return this;
    }

    public void setPercentRank(Double percentRank) {
        this.percentRank = percentRank;
    }

    public Integer getNotifyUserCount() {
        return notifyUserCount;
    }

    public BadgePosts notifyUserCount(Integer notifyUserCount) {
        this.notifyUserCount = notifyUserCount;
        return this;
    }

    public void setNotifyUserCount(Integer notifyUserCount) {
        this.notifyUserCount = notifyUserCount;
    }

    public Integer getLikeScore() {
        return likeScore;
    }

    public BadgePosts likeScore(Integer likeScore) {
        this.likeScore = likeScore;
        return this;
    }

    public void setLikeScore(Integer likeScore) {
        this.likeScore = likeScore;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public BadgePosts deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public String getEditReason() {
        return editReason;
    }

    public BadgePosts editReason(String editReason) {
        this.editReason = editReason;
        return this;
    }

    public void setEditReason(String editReason) {
        this.editReason = editReason;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public BadgePosts wordCount(Integer wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getVersion() {
        return version;
    }

    public BadgePosts version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCookMethod() {
        return cookMethod;
    }

    public BadgePosts cookMethod(Integer cookMethod) {
        this.cookMethod = cookMethod;
        return this;
    }

    public void setCookMethod(Integer cookMethod) {
        this.cookMethod = cookMethod;
    }

    public Boolean isWiki() {
        return wiki;
    }

    public BadgePosts wiki(Boolean wiki) {
        this.wiki = wiki;
        return this;
    }

    public void setWiki(Boolean wiki) {
        this.wiki = wiki;
    }

    public Instant getBakedAt() {
        return bakedAt;
    }

    public BadgePosts bakedAt(Instant bakedAt) {
        this.bakedAt = bakedAt;
        return this;
    }

    public void setBakedAt(Instant bakedAt) {
        this.bakedAt = bakedAt;
    }

    public Integer getBakedVersion() {
        return bakedVersion;
    }

    public BadgePosts bakedVersion(Integer bakedVersion) {
        this.bakedVersion = bakedVersion;
        return this;
    }

    public void setBakedVersion(Integer bakedVersion) {
        this.bakedVersion = bakedVersion;
    }

    public Instant getHiddenAt() {
        return hiddenAt;
    }

    public BadgePosts hiddenAt(Instant hiddenAt) {
        this.hiddenAt = hiddenAt;
        return this;
    }

    public void setHiddenAt(Instant hiddenAt) {
        this.hiddenAt = hiddenAt;
    }

    public Integer getSelfEdits() {
        return selfEdits;
    }

    public BadgePosts selfEdits(Integer selfEdits) {
        this.selfEdits = selfEdits;
        return this;
    }

    public void setSelfEdits(Integer selfEdits) {
        this.selfEdits = selfEdits;
    }

    public Boolean isReplyQuoted() {
        return replyQuoted;
    }

    public BadgePosts replyQuoted(Boolean replyQuoted) {
        this.replyQuoted = replyQuoted;
        return this;
    }

    public void setReplyQuoted(Boolean replyQuoted) {
        this.replyQuoted = replyQuoted;
    }

    public Boolean isViaEmail() {
        return viaEmail;
    }

    public BadgePosts viaEmail(Boolean viaEmail) {
        this.viaEmail = viaEmail;
        return this;
    }

    public void setViaEmail(Boolean viaEmail) {
        this.viaEmail = viaEmail;
    }

    public String getRawEmail() {
        return rawEmail;
    }

    public BadgePosts rawEmail(String rawEmail) {
        this.rawEmail = rawEmail;
        return this;
    }

    public void setRawEmail(String rawEmail) {
        this.rawEmail = rawEmail;
    }

    public Integer getPublicVersion() {
        return publicVersion;
    }

    public BadgePosts publicVersion(Integer publicVersion) {
        this.publicVersion = publicVersion;
        return this;
    }

    public void setPublicVersion(Integer publicVersion) {
        this.publicVersion = publicVersion;
    }

    public String getActionCode() {
        return actionCode;
    }

    public BadgePosts actionCode(String actionCode) {
        this.actionCode = actionCode;
        return this;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getLockedById() {
        return lockedById;
    }

    public BadgePosts lockedById(String lockedById) {
        this.lockedById = lockedById;
        return this;
    }

    public void setLockedById(String lockedById) {
        this.lockedById = lockedById;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public BadgePosts imageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
        return this;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgePosts)) {
            return false;
        }
        return id != null && id.equals(((BadgePosts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BadgePosts{" +
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
