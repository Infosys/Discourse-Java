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
 * A DTO for the {@link com.infy.domain.UserVisits} entity.
 */
public class UserVisitsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private LocalDate visitedAt;

    private Integer postsRead;

    private Boolean mobile;

    @NotNull
    private Integer timeRead;


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

    public LocalDate getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(LocalDate visitedAt) {
        this.visitedAt = visitedAt;
    }

    public Integer getPostsRead() {
        return postsRead;
    }

    public void setPostsRead(Integer postsRead) {
        this.postsRead = postsRead;
    }

    public Boolean isMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Integer getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(Integer timeRead) {
        this.timeRead = timeRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserVisitsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserVisitsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserVisitsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", visitedAt='" + getVisitedAt() + "'" +
            ", postsRead=" + getPostsRead() +
            ", mobile='" + isMobile() + "'" +
            ", timeRead=" + getTimeRead() +
            "}";
    }
}
