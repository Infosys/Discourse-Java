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
 * A DTO for the {@link com.infy.domain.ReviewableHistories} entity.
 */
public class ReviewableHistoriesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long reviewableId;

    @NotNull
    private Integer reviewableHistoryType;

    @NotNull
    private Integer status;

    private String edited;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewableId() {
        return reviewableId;
    }

    public void setReviewableId(Long reviewableId) {
        this.reviewableId = reviewableId;
    }

    public Integer getReviewableHistoryType() {
        return reviewableHistoryType;
    }

    public void setReviewableHistoryType(Integer reviewableHistoryType) {
        this.reviewableHistoryType = reviewableHistoryType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewableHistoriesDTO)) {
            return false;
        }

        return id != null && id.equals(((ReviewableHistoriesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReviewableHistoriesDTO{" +
            "id=" + getId() +
            ", reviewableId=" + getReviewableId() +
            ", reviewableHistoryType=" + getReviewableHistoryType() +
            ", status=" + getStatus() +
            ", edited='" + getEdited() + "'" +
            "}";
    }
}
