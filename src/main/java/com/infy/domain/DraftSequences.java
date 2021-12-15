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
 * A DraftSequences.
 */
@Entity
@Table(name = "draft_sequences")
public class DraftSequences extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "draft_key", nullable = false)
    private String draftKey;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Long sequence;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public DraftSequences userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDraftKey() {
        return draftKey;
    }

    public DraftSequences draftKey(String draftKey) {
        this.draftKey = draftKey;
        return this;
    }

    public void setDraftKey(String draftKey) {
        this.draftKey = draftKey;
    }

    public Long getSequence() {
        return sequence;
    }

    public DraftSequences sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DraftSequences)) {
            return false;
        }
        return id != null && id.equals(((DraftSequences) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DraftSequences{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", draftKey='" + getDraftKey() + "'" +
            ", sequence=" + getSequence() +
            "}";
    }
}
