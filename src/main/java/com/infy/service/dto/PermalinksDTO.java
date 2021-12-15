/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.Permalinks} entity.
 */
public class PermalinksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    private Long topicId;

    private Long postId;

    private Long categoryId;

    private String externalUrl;

    private Long tagId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PermalinksDTO)) {
            return false;
        }

        return id != null && id.equals(((PermalinksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermalinksDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            ", categoryId=" + getCategoryId() +
            ", externalUrl='" + getExternalUrl() + "'" +
            ", tagId=" + getTagId() +
            "}";
    }
}
