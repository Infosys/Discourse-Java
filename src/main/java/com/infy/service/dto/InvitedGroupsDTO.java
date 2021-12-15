/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.InvitedGroups} entity.
 */
public class InvitedGroupsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long groupId;

    private Long inviteId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getInviteId() {
        return inviteId;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitedGroupsDTO)) {
            return false;
        }

        return id != null && id.equals(((InvitedGroupsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvitedGroupsDTO{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", inviteId=" + getInviteId() +
            "}";
    }
}
