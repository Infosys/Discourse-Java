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
 * A DTO for the {@link com.infy.domain.QuotedPosts} entity.
 */
public class QuotedPostsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private Long quotedPostId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getQuotedPostId() {
        return quotedPostId;
    }

    public void setQuotedPostId(Long quotedPostId) {
        this.quotedPostId = quotedPostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuotedPostsDTO)) {
            return false;
        }

        return id != null && id.equals(((QuotedPostsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuotedPostsDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", quotedPostId=" + getQuotedPostId() +
            "}";
    }
}
