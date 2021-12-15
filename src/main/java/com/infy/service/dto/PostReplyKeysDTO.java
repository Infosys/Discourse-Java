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
 * A DTO for the {@link com.infy.domain.PostReplyKeys} entity.
 */
public class PostReplyKeysDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Long postId;

    @NotNull
    private String replyKey;


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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getReplyKey() {
        return replyKey;
    }

    public void setReplyKey(String replyKey) {
        this.replyKey = replyKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostReplyKeysDTO)) {
            return false;
        }

        return id != null && id.equals(((PostReplyKeysDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostReplyKeysDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", postId=" + getPostId() +
            ", replyKey='" + getReplyKey() + "'" +
            "}";
    }
}
