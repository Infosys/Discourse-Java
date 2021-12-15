/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Posts.
 */
@Entity
@Table(name = "posts")
public class Posts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "post_number", nullable = false)
    private Integer postNumber;


    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "raw", nullable = false)
    private String raw;

    @NotNull
    @Column(name = "cooked", nullable = false)
    private String cooked;

    @Column(name = "reply_to_post_number")
    private Long replyToPostNumber;

    @NotNull
    @Column(name = "reply_count", nullable = false)
    private Integer replyCount;

    @NotNull
    @Column(name = "quote_count", nullable = false)
    private Integer quoteCount;

    @NotNull
    @Column(name = "post_status", nullable = false)
    private Integer postStatus;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "off_topic_count", nullable = false)
    private Integer offTopicCount;

    @NotNull
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @NotNull
    @Column(name = "incoming_link_count", nullable = false)
    private Integer incomingLinkCount;

    @NotNull
    @Column(name = "bookmark_count", nullable = false)
    private Integer bookmarkCount;

    @Column(name = "score")
    private Double score;

    @NotNull
    @Column(name = "reads", nullable = false)
    private Integer reads;

    @NotNull
    @Column(name = "post_type", nullable = false)
    private Integer postType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "last_editor_id")
    private String lastEditorId;

    @NotNull
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;

    @Column(name = "hidden_reason_id")
    private Long hiddenReasonId;

    @NotNull
    @Column(name = "notify_moderators_count", nullable = false)
    private Integer notifyModeratorsCount;

    @NotNull
    @Column(name = "spam_count", nullable = false)
    private Integer spamCount;

    @NotNull
    @Column(name = "illegal_count", nullable = false)
    private Integer illegalCount;

    @NotNull
    @Column(name = "inappropriate_count", nullable = false)
    private Integer inappropriateCount;

    @NotNull
    @Column(name = "last_version_at", nullable = false)
    private Instant lastVersionAt;

    @NotNull
    @Column(name = "user_deleted", nullable = false)
    private Boolean userDeleted;

    @Column(name = "reply_to_user_id")
    private String replyToUserId;

    @Column(name = "percent_rank")
    private Double percentRank;

    @NotNull
    @Column(name = "notify_user_count", nullable = false)
    private Integer notifyUserCount;

    @NotNull
    @Column(name = "like_score", nullable = false)
    private Integer likeScore;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "edit_reason")
    private String editReason;

    @Column(name = "word_count")
    private Integer wordCount;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "cook_method", nullable = false)
    private Integer cookMethod;

    @NotNull
    @Column(name = "wiki", nullable = false)
    private Boolean wiki;

    @Column(name = "baked_at")
    private Instant bakedAt;

    @Column(name = "baked_version")
    private Integer bakedVersion;

    @Column(name = "hidden_at")
    private Instant hiddenAt;

    @NotNull
    @Column(name = "self_edits", nullable = false)
    private Integer selfEdits;

    @NotNull
    @Column(name = "reply_quoted", nullable = false)
    private Boolean replyQuoted;

    @NotNull
    @Column(name = "via_email", nullable = false)
    private Boolean viaEmail;

    @Column(name = "raw_email")
    private String rawEmail;

    @NotNull
    @Column(name = "public_version", nullable = false)
    private Integer publicVersion;

    @Column(name = "action_code")
    private String actionCode;

    @Column(name = "locked_by_id")
    private String lockedById;

    @Column(name = "image_upload_id")
    private Long imageUploadId;

    @ManyToOne
    @JsonIgnoreProperties(value = "posts", allowSetters = true)
    private Polls polls;

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

    public Posts userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Posts topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public Posts postNumber(Integer postNumber) {
        this.postNumber = postNumber;
        return this;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public String getRaw() {
        return raw;
    }

    public Posts raw(String raw) {
        this.raw = raw;
        return this;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getCooked() {
        return cooked;
    }

    public Posts cooked(String cooked) {
        this.cooked = cooked;
        return this;
    }

    public void setCooked(String cooked) {
        this.cooked = cooked;
    }

    public Long getReplyToPostNumber() {
        return replyToPostNumber;
    }

    public Posts replyToPostNumber(Long replyToPostNumber) {
        this.replyToPostNumber = replyToPostNumber;
        return this;
    }

    public void setReplyToPostNumber(Long replyToPostNumber) {
        this.replyToPostNumber = replyToPostNumber;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public Posts replyCount(Integer replyCount) {
        this.replyCount = replyCount;
        return this;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getQuoteCount() {
        return quoteCount;
    }

    public Posts quoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
        return this;
    }

    public void setQuoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public Posts postStatus(Integer postStatus) {
        this.postStatus = postStatus;
        return this;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Posts deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getOffTopicCount() {
        return offTopicCount;
    }

    public Posts offTopicCount(Integer offTopicCount) {
        this.offTopicCount = offTopicCount;
        return this;
    }

    public void setOffTopicCount(Integer offTopicCount) {
        this.offTopicCount = offTopicCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public Posts likeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getIncomingLinkCount() {
        return incomingLinkCount;
    }

    public Posts incomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
        return this;
    }

    public void setIncomingLinkCount(Integer incomingLinkCount) {
        this.incomingLinkCount = incomingLinkCount;
    }

    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    public Posts bookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
        return this;
    }

    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public Double getScore() {
        return score;
    }

    public Posts score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getReads() {
        return reads;
    }

    public Posts reads(Integer reads) {
        this.reads = reads;
        return this;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public Integer getPostType() {
        return postType;
    }

    public Posts postType(Integer postType) {
        this.postType = postType;
        return this;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Posts sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getLastEditorId() {
        return lastEditorId;
    }

    public Posts lastEditorId(String lastEditorId) {
        this.lastEditorId = lastEditorId;
        return this;
    }

    public void setLastEditorId(String lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public Posts hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Long getHiddenReasonId() {
        return hiddenReasonId;
    }

    public Posts hiddenReasonId(Long hiddenReasonId) {
        this.hiddenReasonId = hiddenReasonId;
        return this;
    }

    public void setHiddenReasonId(Long hiddenReasonId) {
        this.hiddenReasonId = hiddenReasonId;
    }

    public Integer getNotifyModeratorsCount() {
        return notifyModeratorsCount;
    }

    public Posts notifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
        return this;
    }

    public void setNotifyModeratorsCount(Integer notifyModeratorsCount) {
        this.notifyModeratorsCount = notifyModeratorsCount;
    }

    public Integer getSpamCount() {
        return spamCount;
    }

    public Posts spamCount(Integer spamCount) {
        this.spamCount = spamCount;
        return this;
    }

    public void setSpamCount(Integer spamCount) {
        this.spamCount = spamCount;
    }

    public Integer getIllegalCount() {
        return illegalCount;
    }

    public Posts illegalCount(Integer illegalCount) {
        this.illegalCount = illegalCount;
        return this;
    }

    public void setIllegalCount(Integer illegalCount) {
        this.illegalCount = illegalCount;
    }

    public Integer getInappropriateCount() {
        return inappropriateCount;
    }

    public Posts inappropriateCount(Integer inappropriateCount) {
        this.inappropriateCount = inappropriateCount;
        return this;
    }

    public void setInappropriateCount(Integer inappropriateCount) {
        this.inappropriateCount = inappropriateCount;
    }

    public Instant getLastVersionAt() {
        return lastVersionAt;
    }

    public Posts lastVersionAt(Instant lastVersionAt) {
        this.lastVersionAt = lastVersionAt;
        return this;
    }

    public void setLastVersionAt(Instant lastVersionAt) {
        this.lastVersionAt = lastVersionAt;
    }

    public Boolean isUserDeleted() {
        return userDeleted;
    }

    public Posts userDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
    }

    public String getReplyToUserId() {
        return replyToUserId;
    }

    public Posts replyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
        return this;
    }

    public void setReplyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public Double getPercentRank() {
        return percentRank;
    }

    public Posts percentRank(Double percentRank) {
        this.percentRank = percentRank;
        return this;
    }

    public void setPercentRank(Double percentRank) {
        this.percentRank = percentRank;
    }

    public Integer getNotifyUserCount() {
        return notifyUserCount;
    }

    public Posts notifyUserCount(Integer notifyUserCount) {
        this.notifyUserCount = notifyUserCount;
        return this;
    }

    public void setNotifyUserCount(Integer notifyUserCount) {
        this.notifyUserCount = notifyUserCount;
    }

    public Integer getLikeScore() {
        return likeScore;
    }

    public Posts likeScore(Integer likeScore) {
        this.likeScore = likeScore;
        return this;
    }

    public void setLikeScore(Integer likeScore) {
        this.likeScore = likeScore;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public Posts deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }

    public String getEditReason() {
        return editReason;
    }

    public Posts editReason(String editReason) {
        this.editReason = editReason;
        return this;
    }

    public void setEditReason(String editReason) {
        this.editReason = editReason;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public Posts wordCount(Integer wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getVersion() {
        return version;
    }

    public Posts version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCookMethod() {
        return cookMethod;
    }

    public Posts cookMethod(Integer cookMethod) {
        this.cookMethod = cookMethod;
        return this;
    }

    public void setCookMethod(Integer cookMethod) {
        this.cookMethod = cookMethod;
    }

    public Boolean isWiki() {
        return wiki;
    }

    public Posts wiki(Boolean wiki) {
        this.wiki = wiki;
        return this;
    }

    public void setWiki(Boolean wiki) {
        this.wiki = wiki;
    }

    public Instant getBakedAt() {
        return bakedAt;
    }

    public Posts bakedAt(Instant bakedAt) {
        this.bakedAt = bakedAt;
        return this;
    }

    public void setBakedAt(Instant bakedAt) {
        this.bakedAt = bakedAt;
    }

    public Integer getBakedVersion() {
        return bakedVersion;
    }

    public Posts bakedVersion(Integer bakedVersion) {
        this.bakedVersion = bakedVersion;
        return this;
    }

    public void setBakedVersion(Integer bakedVersion) {
        this.bakedVersion = bakedVersion;
    }

    public Instant getHiddenAt() {
        return hiddenAt;
    }

    public Posts hiddenAt(Instant hiddenAt) {
        this.hiddenAt = hiddenAt;
        return this;
    }

    public void setHiddenAt(Instant hiddenAt) {
        this.hiddenAt = hiddenAt;
    }

    public Integer getSelfEdits() {
        return selfEdits;
    }

    public Posts selfEdits(Integer selfEdits) {
        this.selfEdits = selfEdits;
        return this;
    }

    public void setSelfEdits(Integer selfEdits) {
        this.selfEdits = selfEdits;
    }

    public Boolean isReplyQuoted() {
        return replyQuoted;
    }

    public Posts replyQuoted(Boolean replyQuoted) {
        this.replyQuoted = replyQuoted;
        return this;
    }

    public void setReplyQuoted(Boolean replyQuoted) {
        this.replyQuoted = replyQuoted;
    }

    public Boolean isViaEmail() {
        return viaEmail;
    }

    public Posts viaEmail(Boolean viaEmail) {
        this.viaEmail = viaEmail;
        return this;
    }

    public void setViaEmail(Boolean viaEmail) {
        this.viaEmail = viaEmail;
    }

    public String getRawEmail() {
        return rawEmail;
    }

    public Posts rawEmail(String rawEmail) {
        this.rawEmail = rawEmail;
        return this;
    }

    public void setRawEmail(String rawEmail) {
        this.rawEmail = rawEmail;
    }

    public Integer getPublicVersion() {
        return publicVersion;
    }

    public Posts publicVersion(Integer publicVersion) {
        this.publicVersion = publicVersion;
        return this;
    }

    public void setPublicVersion(Integer publicVersion) {
        this.publicVersion = publicVersion;
    }

    public String getActionCode() {
        return actionCode;
    }

    public Posts actionCode(String actionCode) {
        this.actionCode = actionCode;
        return this;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getLockedById() {
        return lockedById;
    }

    public Posts lockedById(String lockedById) {
        this.lockedById = lockedById;
        return this;
    }

    public void setLockedById(String lockedById) {
        this.lockedById = lockedById;
    }

    public Long getImageUploadId() {
        return imageUploadId;
    }

    public Posts imageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
        return this;
    }

    public void setImageUploadId(Long imageUploadId) {
        this.imageUploadId = imageUploadId;
    }

    public Polls getPolls() {
        return polls;
    }

    public Posts polls(Polls polls) {
        this.polls = polls;
        return this;
    }

    public void setPolls(Polls polls) {
        this.polls = polls;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Posts)) {
            return false;
        }
        return id != null && id.equals(((Posts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Posts{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", topicId=" + getTopicId() +
            ", postNumber=" + getPostNumber() +
            ", raw='" + getRaw() + "'" +
            ", cooked='" + getCooked() + "'" +
            ", replyToPostNumber=" + getReplyToPostNumber() +
            ", replyCount=" + getReplyCount() +
            ", quoteCount=" + getQuoteCount() +
            ", postStatus=" + getPostStatus() +
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
