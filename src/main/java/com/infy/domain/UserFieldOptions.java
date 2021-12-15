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
 * A UserFieldOptions.
 */
@Entity
@Table(name = "user_field_options")
public class UserFieldOptions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_field_id", nullable = false)
    private Long userFieldId;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserFieldId() {
        return userFieldId;
    }

    public UserFieldOptions userFieldId(Long userFieldId) {
        this.userFieldId = userFieldId;
        return this;
    }

    public void setUserFieldId(Long userFieldId) {
        this.userFieldId = userFieldId;
    }

    public String getValue() {
        return value;
    }

    public UserFieldOptions value(String value) {
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
        if (!(o instanceof UserFieldOptions)) {
            return false;
        }
        return id != null && id.equals(((UserFieldOptions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFieldOptions{" +
            "id=" + getId() +
            ", userFieldId=" + getUserFieldId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
