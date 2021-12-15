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
 * A DTO for the {@link com.infy.domain.EmbeddableHosts} entity.
 */
public class EmbeddableHostsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String host;

    @NotNull
    private Long categoryId;

    private String className;

    private String allowedPaths;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAllowedPaths() {
        return allowedPaths;
    }

    public void setAllowedPaths(String allowedPaths) {
        this.allowedPaths = allowedPaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmbeddableHostsDTO)) {
            return false;
        }

        return id != null && id.equals(((EmbeddableHostsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmbeddableHostsDTO{" +
            "id=" + getId() +
            ", host='" + getHost() + "'" +
            ", categoryId=" + getCategoryId() +
            ", className='" + getClassName() + "'" +
            ", allowedPaths='" + getAllowedPaths() + "'" +
            "}";
    }
}
