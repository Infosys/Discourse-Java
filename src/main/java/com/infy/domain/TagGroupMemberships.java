/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TagGroupMemberships.
 */
@Entity
@Table(name = "tag_group_memberships")
public class TagGroupMemberships extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @NotNull
    @Column(name = "tag_group_id", nullable = false)
    private Long tagGroupId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public TagGroupMemberships tagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagGroupId() {
        return tagGroupId;
    }

    public TagGroupMemberships tagGroupId(Long tagGroupId) {
        this.tagGroupId = tagGroupId;
        return this;
    }

    public void setTagGroupId(Long tagGroupId) {
        this.tagGroupId = tagGroupId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagGroupMemberships)) {
            return false;
        }
        return id != null && id.equals(((TagGroupMemberships) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagGroupMemberships{" +
            "id=" + getId() +
            ", tagId=" + getTagId() +
            ", tagGroupId=" + getTagGroupId() +
            "}";
    }
}
