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
 * A DTO for the {@link com.infy.domain.TopicLinks} entity.
 */
public class TopicLinksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long topicId;

    private Long postId;

    @NotNull
    private String userId;

    @NotNull
    private String url;

    @NotNull
    private String domain;

    @NotNull
    private Boolean internal;

    private Long linkTopicId;

    private Boolean reflection;

    @NotNull
    private Integer clicks;

    private Long linkPostId;

    private String title;

    private Instant crawledAt;

    @NotNull
    private Boolean quote;

    private String extension;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean isInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public Long getLinkTopicId() {
        return linkTopicId;
    }

    public void setLinkTopicId(Long linkTopicId) {
        this.linkTopicId = linkTopicId;
    }

    public Boolean isReflection() {
        return reflection;
    }

    public void setReflection(Boolean reflection) {
        this.reflection = reflection;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Long getLinkPostId() {
        return linkPostId;
    }

    public void setLinkPostId(Long linkPostId) {
        this.linkPostId = linkPostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCrawledAt() {
        return crawledAt;
    }

    public void setCrawledAt(Instant crawledAt) {
        this.crawledAt = crawledAt;
    }

    public Boolean isQuote() {
        return quote;
    }

    public void setQuote(Boolean quote) {
        this.quote = quote;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicLinksDTO)) {
            return false;
        }

        return id != null && id.equals(((TopicLinksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicLinksDTO{" +
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
