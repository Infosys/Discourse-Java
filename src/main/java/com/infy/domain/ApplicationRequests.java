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
import java.time.LocalDate;

/**
 * A ApplicationRequests.
 */
@Entity
@Table(name = "application_requests")
public class ApplicationRequests extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "req_type", nullable = false)
    private Long reqType;

    @NotNull
    @Column(name = "count", nullable = false)
    private Long count;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ApplicationRequests date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getReqType() {
        return reqType;
    }

    public ApplicationRequests reqType(Long reqType) {
        this.reqType = reqType;
        return this;
    }

    public void setReqType(Long reqType) {
        this.reqType = reqType;
    }

    public Long getCount() {
        return count;
    }

    public ApplicationRequests count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationRequests)) {
            return false;
        }
        return id != null && id.equals(((ApplicationRequests) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationRequests{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", reqType=" + getReqType() +
            ", count=" + getCount() +
            "}";
    }
}
