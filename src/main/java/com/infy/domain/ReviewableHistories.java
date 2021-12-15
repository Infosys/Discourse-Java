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
 * A ReviewableHistories.
 */
@Entity
@Table(name = "reviewable_histories")
public class ReviewableHistories extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reviewable_id", nullable = false)
    private Long reviewableId;

    @NotNull
    @Column(name = "reviewable_history_type", nullable = false)
    private Integer reviewableHistoryType;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "edited")
    private String edited;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewableId() {
        return reviewableId;
    }

    public ReviewableHistories reviewableId(Long reviewableId) {
        this.reviewableId = reviewableId;
        return this;
    }

    public void setReviewableId(Long reviewableId) {
        this.reviewableId = reviewableId;
    }

    public Integer getReviewableHistoryType() {
        return reviewableHistoryType;
    }

    public ReviewableHistories reviewableHistoryType(Integer reviewableHistoryType) {
        this.reviewableHistoryType = reviewableHistoryType;
        return this;
    }

    public void setReviewableHistoryType(Integer reviewableHistoryType) {
        this.reviewableHistoryType = reviewableHistoryType;
    }

    public Integer getStatus() {
        return status;
    }

    public ReviewableHistories status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEdited() {
        return edited;
    }

    public ReviewableHistories edited(String edited) {
        this.edited = edited;
        return this;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewableHistories)) {
            return false;
        }
        return id != null && id.equals(((ReviewableHistories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewableHistories{" +
            "id=" + getId() +
            ", reviewableId=" + getReviewableId() +
            ", reviewableHistoryType=" + getReviewableHistoryType() +
            ", status=" + getStatus() +
            ", edited='" + getEdited() + "'" +
            "}";
    }
}
