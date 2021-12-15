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
 * A DTO for the {@link com.infy.domain.PostRevisions} entity.
 */
public class PostRevisionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String userId;

    private Long postId;

    private String modifications;

    private Integer number;

    @NotNull
    private Boolean hidden;


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

    public String getModifications() {
        return modifications;
    }

    public void setModifications(String modifications) {
        this.modifications = modifications;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostRevisionsDTO)) {
            return false;
        }

        return id != null && id.equals(((PostRevisionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostRevisionsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", postId=" + getPostId() +
            ", modifications='" + getModifications() + "'" +
            ", number=" + getNumber() +
            ", hidden='" + isHidden() + "'" +
            "}";
    }
}
