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
 * A PluginStoreRows.
 */
@Entity
@Table(name = "plugin_store_rows")
public class PluginStoreRows extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "plugin_name", nullable = false)
    private String pluginName;

    @NotNull
    @Column(name = "key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "value")
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPluginName() {
        return pluginName;
    }

    public PluginStoreRows pluginName(String pluginName) {
        this.pluginName = pluginName;
        return this;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getKey() {
        return key;
    }

    public PluginStoreRows key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTypeName() {
        return typeName;
    }

    public PluginStoreRows typeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public PluginStoreRows value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PluginStoreRows)) {
            return false;
        }
        return id != null && id.equals(((PluginStoreRows) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PluginStoreRows{" +
            "id=" + getId() +
            ", pluginName='" + getPluginName() + "'" +
            ", key='" + getKey() + "'" +
            ", typeName='" + getTypeName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
