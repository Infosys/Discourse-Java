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
 * A ChildThemes.
 */
@Entity
@Table(name = "child_themes")
public class ChildThemes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "parent_theme_id")
    private Long parentThemeId;

    @Column(name = "child_theme_id")
    private Long childThemeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentThemeId() {
        return parentThemeId;
    }

    public ChildThemes parentThemeId(Long parentThemeId) {
        this.parentThemeId = parentThemeId;
        return this;
    }

    public void setParentThemeId(Long parentThemeId) {
        this.parentThemeId = parentThemeId;
    }

    public Long getChildThemeId() {
        return childThemeId;
    }

    public ChildThemes childThemeId(Long childThemeId) {
        this.childThemeId = childThemeId;
        return this;
    }

    public void setChildThemeId(Long childThemeId) {
        this.childThemeId = childThemeId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildThemes)) {
            return false;
        }
        return id != null && id.equals(((ChildThemes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildThemes{" +
            "id=" + getId() +
            ", parentThemeId=" + getParentThemeId() +
            ", childThemeId=" + getChildThemeId() +
            "}";
    }
}
