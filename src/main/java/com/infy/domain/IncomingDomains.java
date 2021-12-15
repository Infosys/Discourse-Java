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
 * A IncomingDomains.
 */
@Entity
@Table(name = "incoming_domains")
public class IncomingDomains extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "https", nullable = false)
    private Boolean https;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

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

    public IncomingDomains name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isHttps() {
        return https;
    }

    public IncomingDomains https(Boolean https) {
        this.https = https;
        return this;
    }

    public void setHttps(Boolean https) {
        this.https = https;
    }

    public Integer getPort() {
        return port;
    }

    public IncomingDomains port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingDomains)) {
            return false;
        }
        return id != null && id.equals(((IncomingDomains) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingDomains{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", https='" + isHttps() + "'" +
            ", port=" + getPort() +
            "}";
    }
}
