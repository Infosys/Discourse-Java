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
 * A ColorSchemeColors.
 */
@Entity
@Table(name = "color_scheme_colors")
public class ColorSchemeColors extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "hex", nullable = false)
    private String hex;

    @NotNull
    @Column(name = "color_scheme_id", nullable = false)
    private Long colorSchemeId;

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

    public ColorSchemeColors name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public ColorSchemeColors hex(String hex) {
        this.hex = hex;
        return this;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Long getColorSchemeId() {
        return colorSchemeId;
    }

    public ColorSchemeColors colorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
        return this;
    }

    public void setColorSchemeId(Long colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColorSchemeColors)) {
            return false;
        }
        return id != null && id.equals(((ColorSchemeColors) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColorSchemeColors{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hex='" + getHex() + "'" +
            ", colorSchemeId=" + getColorSchemeId() +
            "}";
    }
}
