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
 * A TopicLinks.
 */
@Entity
@Table(name = "topic_links")
public class TopicLinks extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    @Column(name = "post_id")
    private Long postId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "domain", nullable = false)
    private String domain;

    @NotNull
    @Column(name = "internal", nullable = false)
    private Boolean internal;

    @Column(name = "link_topic_id")
    private Long linkTopicId;

    @Column(name = "reflection")
    private Boolean reflection;

    @NotNull
    @Column(name = "clicks", nullable = false)
    private Integer clicks;

    @Column(name = "link_post_id")
    private Long linkPostId;

    @Column(name = "title")
    private String title;

    @Column(name = "crawled_at")
    private Instant crawledAt;

    @NotNull
    @Column(name = "quote", nullable = false)
    private Boolean quote;

    @Column(name = "extension")
    private String extension;

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

    public TopicLinks topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostId() {
        return postId;
    }

    public TopicLinks postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public TopicLinks userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public TopicLinks url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public TopicLinks domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean isInternal() {
        return internal;
    }

    public TopicLinks internal(Boolean internal) {
        this.internal = internal;
        return this;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public Long getLinkTopicId() {
        return linkTopicId;
    }

    public TopicLinks linkTopicId(Long linkTopicId) {
        this.linkTopicId = linkTopicId;
        return this;
    }

    public void setLinkTopicId(Long linkTopicId) {
        this.linkTopicId = linkTopicId;
    }

    public Boolean isReflection() {
        return reflection;
    }

    public TopicLinks reflection(Boolean reflection) {
        this.reflection = reflection;
        return this;
    }

    public void setReflection(Boolean reflection) {
        this.reflection = reflection;
    }

    public Integer getClicks() {
        return clicks;
    }

    public TopicLinks clicks(Integer clicks) {
        this.clicks = clicks;
        return this;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Long getLinkPostId() {
        return linkPostId;
    }

    public TopicLinks linkPostId(Long linkPostId) {
        this.linkPostId = linkPostId;
        return this;
    }

    public void setLinkPostId(Long linkPostId) {
        this.linkPostId = linkPostId;
    }

    public String getTitle() {
        return title;
    }

    public TopicLinks title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCrawledAt() {
        return crawledAt;
    }

    public TopicLinks crawledAt(Instant crawledAt) {
        this.crawledAt = crawledAt;
        return this;
    }

    public void setCrawledAt(Instant crawledAt) {
        this.crawledAt = crawledAt;
    }

    public Boolean isQuote() {
        return quote;
    }

    public TopicLinks quote(Boolean quote) {
        this.quote = quote;
        return this;
    }

    public void setQuote(Boolean quote) {
        this.quote = quote;
    }

    public String getExtension() {
        return extension;
    }

    public TopicLinks extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicLinks)) {
            return false;
        }
        return id != null && id.equals(((TopicLinks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicLinks{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            ", userId='" + getUserId() + "'" +
            ", url='" + getUrl() + "'" +
            ", domain='" + getDomain() + "'" +
            ", internal='" + isInternal() + "'" +
            ", linkTopicId=" + getLinkTopicId() +
            ", reflection='" + isReflection() + "'" +
            ", clicks=" + getClicks() +
            ", linkPostId=" + getLinkPostId() +
            ", title='" + getTitle() + "'" +
            ", crawledAt='" + getCrawledAt() + "'" +
            ", quote='" + isQuote() + "'" +
            ", extension='" + getExtension() + "'" +
            "}";
    }
}
