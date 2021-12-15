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
 * A UserVisits.
 */
@Entity
@Table(name = "user_visits")
public class UserVisits extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "visited_at", nullable = false)
    private LocalDate visitedAt;

    @Column(name = "posts_read")
    private Integer postsRead;

    @Column(name = "mobile")
    private Boolean mobile;

    @NotNull
    @Column(name = "time_read", nullable = false)
    private Integer timeRead;

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

    public UserVisits userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getVisitedAt() {
        return visitedAt;
    }

    public UserVisits visitedAt(LocalDate visitedAt) {
        this.visitedAt = visitedAt;
        return this;
    }

    public void setVisitedAt(LocalDate visitedAt) {
        this.visitedAt = visitedAt;
    }

    public Integer getPostsRead() {
        return postsRead;
    }

    public UserVisits postsRead(Integer postsRead) {
        this.postsRead = postsRead;
        return this;
    }

    public void setPostsRead(Integer postsRead) {
        this.postsRead = postsRead;
    }

    public Boolean isMobile() {
        return mobile;
    }

    public UserVisits mobile(Boolean mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Integer getTimeRead() {
        return timeRead;
    }

    public UserVisits timeRead(Integer timeRead) {
        this.timeRead = timeRead;
        return this;
    }

    public void setTimeRead(Integer timeRead) {
        this.timeRead = timeRead;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserVisits)) {
            return false;
        }
        return id != null && id.equals(((UserVisits) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserVisits{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", visitedAt='" + getVisitedAt() + "'" +
            ", postsRead=" + getPostsRead() +
            ", mobile='" + isMobile() + "'" +
            ", timeRead=" + getTimeRead() +
            "}";
    }
}
