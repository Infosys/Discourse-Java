/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.ChildThemes} entity.
 */
public class ChildThemesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long parentThemeId;

    private Long childThemeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentThemeId() {
        return parentThemeId;
    }

    public void setParentThemeId(Long parentThemeId) {
        this.parentThemeId = parentThemeId;
    }

    public Long getChildThemeId() {
        return childThemeId;
    }

    public void setChildThemeId(Long childThemeId) {
        this.childThemeId = childThemeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildThemesDTO)) {
            return false;
        }

        return id != null && id.equals(((ChildThemesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildThemesDTO{" +
            "id=" + getId() +
            ", parentThemeId=" + getParentThemeId() +
            ", childThemeId=" + getChildThemeId() +
            "}";
    }
}
