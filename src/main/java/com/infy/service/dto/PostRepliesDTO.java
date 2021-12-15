/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.PostReplies} entity.
 */
public class PostRepliesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long postId;

    private Long replyPostId;


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

    public Long getReplyPostId() {
        return replyPostId;
    }

    public void setReplyPostId(Long replyPostId) {
        this.replyPostId = replyPostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostRepliesDTO)) {
            return false;
        }

        return id != null && id.equals(((PostRepliesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostRepliesDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", replyPostId=" + getReplyPostId() +
            "}";
    }
}
