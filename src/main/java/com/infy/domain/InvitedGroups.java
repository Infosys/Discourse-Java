/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A InvitedGroups.
 */
@Entity
@Table(name = "invited_groups")
public class InvitedGroups extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "invite_id")
    private Long inviteId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public InvitedGroups groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getInviteId() {
        return inviteId;
    }

    public InvitedGroups inviteId(Long inviteId) {
        this.inviteId = inviteId;
        return this;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitedGroups)) {
            return false;
        }
        return id != null && id.equals(((InvitedGroups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvitedGroups{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", inviteId=" + getInviteId() +
            "}";
    }
}
