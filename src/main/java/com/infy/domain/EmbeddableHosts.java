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
 * A EmbeddableHosts.
 */
@Entity
@Table(name = "embeddable_hosts")
public class EmbeddableHosts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "host", nullable = false)
    private String host;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "allowed_paths")
    private String allowedPaths;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public EmbeddableHosts host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public EmbeddableHosts categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getClassName() {
        return className;
    }

    public EmbeddableHosts className(String className) {
        this.className = className;
        return this;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAllowedPaths() {
        return allowedPaths;
    }

    public EmbeddableHosts allowedPaths(String allowedPaths) {
        this.allowedPaths = allowedPaths;
        return this;
    }

    public void setAllowedPaths(String allowedPaths) {
        this.allowedPaths = allowedPaths;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmbeddableHosts)) {
            return false;
        }
        return id != null && id.equals(((EmbeddableHosts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmbeddableHosts{" +
            "id=" + getId() +
            ", host='" + getHost() + "'" +
            ", categoryId=" + getCategoryId() +
            ", className='" + getClassName() + "'" +
            ", allowedPaths='" + getAllowedPaths() + "'" +
            "}";
    }
}
