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
 * A DTO for the {@link com.infy.domain.Drafts} entity.
 */
public class DraftsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String draftKey;

    @NotNull
    private String data;

    @NotNull
    private Long sequence;

    @NotNull
    private Integer revisions;

    private String owner;


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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Integer getRevisions() {
        return revisions;
    }

    public void setRevisions(Integer revisions) {
        this.revisions = revisions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DraftsDTO)) {
            return false;
        }

        return id != null && id.equals(((DraftsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DraftsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", draftKey='" + getDraftKey() + "'" +
            ", data='" + getData() + "'" +
            ", sequence=" + getSequence() +
            ", revisions=" + getRevisions() +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
