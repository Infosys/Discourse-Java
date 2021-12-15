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
 * A DTO for the {@link com.infy.domain.PluginStoreRows} entity.
 */
public class PluginStoreRowsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String pluginName;

    @NotNull
    private String key;

    @NotNull
    private String typeName;

    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PluginStoreRowsDTO)) {
            return false;
        }

        return id != null && id.equals(((PluginStoreRowsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PluginStoreRowsDTO{" +
            "id=" + getId() +
            ", pluginName='" + getPluginName() + "'" +
            ", key='" + getKey() + "'" +
            ", typeName='" + getTypeName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
