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
 * A DTO for the {@link com.infy.domain.TagGroupMemberships} entity.
 */
public class TagGroupMembershipsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long tagId;

    @NotNull
    private Long tagGroupId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagGroupId() {
        return tagGroupId;
    }

    public void setTagGroupId(Long tagGroupId) {
        this.tagGroupId = tagGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagGroupMembershipsDTO)) {
            return false;
        }

        return id != null && id.equals(((TagGroupMembershipsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagGroupMembershipsDTO{" +
            "id=" + getId() +
            ", tagId=" + getTagId() +
            ", tagGroupId=" + getTagGroupId() +
            "}";
    }
}
