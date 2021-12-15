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
 * A DTO for the {@link com.infy.domain.IncomingReferers} entity.
 */
public class IncomingReferersDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String path;

    @NotNull
    private Long incomingDomainId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getIncomingDomainId() {
        return incomingDomainId;
    }

    public void setIncomingDomainId(Long incomingDomainId) {
        this.incomingDomainId = incomingDomainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingReferersDTO)) {
            return false;
        }

        return id != null && id.equals(((IncomingReferersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingReferersDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", incomingDomainId=" + getIncomingDomainId() +
            "}";
    }
}
