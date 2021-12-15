/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.PostDetails} entity.
 */
public class PostDetailsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long postId;

    private String key;

    private String value;

    private String extra;


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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((PostDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDetailsDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", extra='" + getExtra() + "'" +
            "}";
    }
}
