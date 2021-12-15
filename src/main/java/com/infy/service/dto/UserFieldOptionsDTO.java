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
 * A DTO for the {@link com.infy.domain.UserFieldOptions} entity.
 */
public class UserFieldOptionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userFieldId;

    @NotNull
    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserFieldId() {
        return userFieldId;
    }

    public void setUserFieldId(Long userFieldId) {
        this.userFieldId = userFieldId;
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
        if (!(o instanceof UserFieldOptionsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserFieldOptionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFieldOptionsDTO{" +
            "id=" + getId() +
            ", userFieldId=" + getUserFieldId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
