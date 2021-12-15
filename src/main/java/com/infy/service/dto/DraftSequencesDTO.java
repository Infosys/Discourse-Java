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
 * A DTO for the {@link com.infy.domain.DraftSequences} entity.
 */
public class DraftSequencesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String draftKey;

    @NotNull
    private Long sequence;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDraftKey() {
        return draftKey;
    }

    public void setDraftKey(String draftKey) {
        this.draftKey = draftKey;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DraftSequencesDTO)) {
            return false;
        }

        return id != null && id.equals(((DraftSequencesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DraftSequencesDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", draftKey='" + getDraftKey() + "'" +
            ", sequence=" + getSequence() +
            "}";
    }
}
