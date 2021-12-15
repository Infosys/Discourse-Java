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
 * A DTO for the {@link com.infy.domain.GroupsWebHooks} entity.
 */
public class GroupsWebHooksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long webHookId;

    @NotNull
    private Long groupId;


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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupsWebHooksDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupsWebHooksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupsWebHooksDTO{" +
            "id=" + getId() +
            ", webHookId=" + getWebHookId() +
            ", groupId=" + getGroupId() +
            "}";
    }
}
