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
 * A GivenDailyLikes.
 */
@Entity
@Table(name = "given_daily_likes")
public class GivenDailyLikes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "likes_given", nullable = false)
    private Integer likesGiven;

    @NotNull
    @Column(name = "given_date", nullable = false)
    private LocalDate givenDate;

    @NotNull
    @Column(name = "limit_reached", nullable = false)
    private Boolean limitReached;

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

    public GivenDailyLikes userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLikesGiven() {
        return likesGiven;
    }

    public GivenDailyLikes likesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
        return this;
    }

    public void setLikesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
    }

    public LocalDate getGivenDate() {
        return givenDate;
    }

    public GivenDailyLikes givenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
        return this;
    }

    public void setGivenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
    }

    public Boolean isLimitReached() {
        return limitReached;
    }

    public GivenDailyLikes limitReached(Boolean limitReached) {
        this.limitReached = limitReached;
        return this;
    }

    public void setLimitReached(Boolean limitReached) {
        this.limitReached = limitReached;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GivenDailyLikes)) {
            return false;
        }
        return id != null && id.equals(((GivenDailyLikes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GivenDailyLikes{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", likesGiven=" + getLikesGiven() +
            ", givenDate='" + getGivenDate() + "'" +
            ", limitReached='" + isLimitReached() + "'" +
            "}";
    }
}
