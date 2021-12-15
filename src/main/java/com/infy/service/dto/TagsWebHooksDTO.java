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
 * A DTO for the {@link com.infy.domain.TagsWebHooks} entity.
 */
public class TagsWebHooksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long webHookId;

    @NotNull
    private Long tagId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebHookId() {
        return webHookId;
    }

    public void setWebHookId(Long webHookId) {
        this.webHookId = webHookId;
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
        if (!(o instanceof TagsWebHooksDTO)) {
            return false;
        }

        return id != null && id.equals(((TagsWebHooksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagsWebHooksDTO{" +
            "id=" + getId() +
            ", webHookId=" + getWebHookId() +
            ", tagId=" + getTagId() +
            "}";
    }
}
