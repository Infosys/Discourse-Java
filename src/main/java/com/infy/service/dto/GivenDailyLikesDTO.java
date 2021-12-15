/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.GivenDailyLikes} entity.
 */
public class GivenDailyLikesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Integer likesGiven;

    @NotNull
    private LocalDate givenDate;

    @NotNull
    private Boolean limitReached;


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

    public Integer getLikesGiven() {
        return likesGiven;
    }

    public void setLikesGiven(Integer likesGiven) {
        this.likesGiven = likesGiven;
    }

    public LocalDate getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
    }

    public Boolean isLimitReached() {
        return limitReached;
    }

    public void setLimitReached(Boolean limitReached) {
        this.limitReached = limitReached;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GivenDailyLikesDTO)) {
            return false;
        }

        return id != null && id.equals(((GivenDailyLikesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GivenDailyLikesDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", likesGiven=" + getLikesGiven() +
            ", givenDate='" + getGivenDate() + "'" +
            ", limitReached='" + isLimitReached() + "'" +
            "}";
    }
}
