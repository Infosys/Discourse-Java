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
 * A IncomingReferers.
 */
@Entity
@Table(name = "incoming_referers")
public class IncomingReferers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Column(name = "incoming_domain_id", nullable = false)
    private Long incomingDomainId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public IncomingReferers path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getIncomingDomainId() {
        return incomingDomainId;
    }

    public IncomingReferers incomingDomainId(Long incomingDomainId) {
        this.incomingDomainId = incomingDomainId;
        return this;
    }

    public void setIncomingDomainId(Long incomingDomainId) {
        this.incomingDomainId = incomingDomainId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingReferers)) {
            return false;
        }
        return id != null && id.equals(((IncomingReferers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingReferers{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", incomingDomainId=" + getIncomingDomainId() +
            "}";
    }
}
