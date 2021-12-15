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
 * A DTO for the {@link com.infy.domain.UnsubscribeKeys} entity.
 */
public class UnsubscribeKeysDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String key;

    @NotNull
    private String userId;

    private String unsubscribeKeyType;

    private Long topicId;

    private Long postId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnsubscribeKeyType() {
        return unsubscribeKeyType;
    }

    public void setUnsubscribeKeyType(String unsubscribeKeyType) {
        this.unsubscribeKeyType = unsubscribeKeyType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnsubscribeKeysDTO)) {
            return false;
        }

        return id != null && id.equals(((UnsubscribeKeysDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnsubscribeKeysDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", userId='" + getUserId() + "'" +
            ", unsubscribeKeyType='" + getUnsubscribeKeyType() + "'" +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            "}";
    }
}
