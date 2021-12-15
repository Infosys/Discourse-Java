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
 * A PostRevisions.
 */
@Entity
@Table(name = "post_revisions")
public class PostRevisions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "modifications")
    private String modifications;

    @Column(name = "number")
    private Integer number;

    @NotNull
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;

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

    public PostRevisions userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public PostRevisions postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getModifications() {
        return modifications;
    }

    public PostRevisions modifications(String modifications) {
        this.modifications = modifications;
        return this;
    }

    public void setModifications(String modifications) {
        this.modifications = modifications;
    }

    public Integer getNumber() {
        return number;
    }

    public PostRevisions number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public PostRevisions hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostRevisions)) {
            return false;
        }
        return id != null && id.equals(((PostRevisions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostRevisions{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", postId=" + getPostId() +
            ", modifications='" + getModifications() + "'" +
            ", number=" + getNumber() +
            ", hidden='" + isHidden() + "'" +
            "}";
    }
}
