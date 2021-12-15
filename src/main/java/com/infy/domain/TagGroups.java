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
 * A TagGroups.
 */
@Entity
@Table(name = "tag_groups")
public class TagGroups extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_tag_id")
    private Long parentTagId;

    @Column(name = "one_per_topic")
    private Boolean onePerTopic;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TagGroups name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentTagId() {
        return parentTagId;
    }

    public TagGroups parentTagId(Long parentTagId) {
        this.parentTagId = parentTagId;
        return this;
    }

    public void setParentTagId(Long parentTagId) {
        this.parentTagId = parentTagId;
    }

    public Boolean isOnePerTopic() {
        return onePerTopic;
    }

    public TagGroups onePerTopic(Boolean onePerTopic) {
        this.onePerTopic = onePerTopic;
        return this;
    }

    public void setOnePerTopic(Boolean onePerTopic) {
        this.onePerTopic = onePerTopic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagGroups)) {
            return false;
        }
        return id != null && id.equals(((TagGroups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagGroups{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentTagId=" + getParentTagId() +
            ", onePerTopic='" + isOnePerTopic() + "'" +
            "}";
    }
}
