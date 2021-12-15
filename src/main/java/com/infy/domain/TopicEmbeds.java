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
 * A TopicEmbeds.
 */
@Entity
@Table(name = "topic_embeds")
public class TopicEmbeds extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @NotNull
    @Column(name = "embed_url", nullable = false)
    private String embedUrl;

    @Column(name = "content_sha_1")
    private String contentSha1;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by_id")
    private String deletedById;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public TopicEmbeds topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostId() {
        return postId;
    }

    public TopicEmbeds postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public TopicEmbeds embedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
        return this;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getContentSha1() {
        return contentSha1;
    }

    public TopicEmbeds contentSha1(String contentSha1) {
        this.contentSha1 = contentSha1;
        return this;
    }

    public void setContentSha1(String contentSha1) {
        this.contentSha1 = contentSha1;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public TopicEmbeds deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedById() {
        return deletedById;
    }

    public TopicEmbeds deletedById(String deletedById) {
        this.deletedById = deletedById;
        return this;
    }

    public void setDeletedById(String deletedById) {
        this.deletedById = deletedById;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicEmbeds)) {
            return false;
        }
        return id != null && id.equals(((TopicEmbeds) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicEmbeds{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            ", embedUrl='" + getEmbedUrl() + "'" +
            ", contentSha1='" + getContentSha1() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedById='" + getDeletedById() + "'" +
            "}";
    }
}
