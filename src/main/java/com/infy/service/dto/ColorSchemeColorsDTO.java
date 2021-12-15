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
 * A DTO for the {@link com.infy.domain.ColorSchemeColors} entity.
 */
public class ColorSchemeColorsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String hex;

    @NotNull
    private Long colorSchemeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Long getColorSchemeId() {
        return colorSchemeId;
    }

    public void setColorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColorSchemeColorsDTO)) {
            return false;
        }

        return id != null && id.equals(((ColorSchemeColorsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColorSchemeColorsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hex='" + getHex() + "'" +
            ", colorSchemeId=" + getColorSchemeId() +
            "}";
    }
}
