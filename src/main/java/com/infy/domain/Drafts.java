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
 * A Drafts.
 */
@Entity
@Table(name = "drafts")
public class Drafts extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "data", nullable = false)
    private String data;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Long sequence;

    @NotNull
    @Column(name = "revisions", nullable = false)
    private Integer revisions;

    @Column(name = "owner")
    private String owner;

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

    public Drafts userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDraftKey() {
        return draftKey;
    }

    public Drafts draftKey(String draftKey) {
        this.draftKey = draftKey;
        return this;
    }

    public void setDraftKey(String draftKey) {
        this.draftKey = draftKey;
    }

    public String getData() {
        return data;
    }

    public Drafts data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getSequence() {
        return sequence;
    }

    public Drafts sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Integer getRevisions() {
        return revisions;
    }

    public Drafts revisions(Integer revisions) {
        this.revisions = revisions;
        return this;
    }

    public void setRevisions(Integer revisions) {
        this.revisions = revisions;
    }

    public String getOwner() {
        return owner;
    }

    public Drafts owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drafts)) {
            return false;
        }
        return id != null && id.equals(((Drafts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drafts{" +
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
