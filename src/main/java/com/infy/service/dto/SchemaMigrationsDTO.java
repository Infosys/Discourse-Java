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
 * A DTO for the {@link com.infy.domain.SchemaMigrations} entity.
 */
public class SchemaMigrationsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemaMigrationsDTO)) {
            return false;
        }

        return id != null && id.equals(((SchemaMigrationsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchemaMigrationsDTO{" +
            "id=" + getId() +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
