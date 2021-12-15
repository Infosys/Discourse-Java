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
 * A DTO for the {@link com.infy.domain.PostTimings} entity.
 */
public class PostTimingsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long topicId;

    @NotNull
    private Integer postNumber;

    @NotNull
    private String userId;

    @NotNull
    private Integer msecs;


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

    public Integer getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMsecs() {
        return msecs;
    }

    public void setMsecs(Integer msecs) {
        this.msecs = msecs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostTimingsDTO)) {
            return false;
        }

        return id != null && id.equals(((PostTimingsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostTimingsDTO{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", postNumber=" + getPostNumber() +
            ", userId='" + getUserId() + "'" +
            ", msecs=" + getMsecs() +
            "}";
    }
}
