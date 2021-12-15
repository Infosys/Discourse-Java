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
 * A SearchLogs.
 */
@Entity
@Table(name = "search_logs")
public class SearchLogs extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "term", nullable = false)
    private String term;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "search_result_id")
    private Long searchResultId;

    @NotNull
    @Column(name = "search_type", nullable = false)
    private Integer searchType;

    @Column(name = "search_result_type")
    private Integer searchResultType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public SearchLogs term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUserId() {
        return userId;
    }

    public SearchLogs userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public SearchLogs ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getSearchResultId() {
        return searchResultId;
    }

    public SearchLogs searchResultId(Long searchResultId) {
        this.searchResultId = searchResultId;
        return this;
    }

    public void setSearchResultId(Long searchResultId) {
        this.searchResultId = searchResultId;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public SearchLogs searchType(Integer searchType) {
        this.searchType = searchType;
        return this;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getSearchResultType() {
        return searchResultType;
    }

    public SearchLogs searchResultType(Integer searchResultType) {
        this.searchResultType = searchResultType;
        return this;
    }

    public void setSearchResultType(Integer searchResultType) {
        this.searchResultType = searchResultType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchLogs)) {
            return false;
        }
        return id != null && id.equals(((SearchLogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SearchLogs{" +
            "id=" + getId() +
            ", term='" + getTerm() + "'" +
            ", userId='" + getUserId() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", searchResultId=" + getSearchResultId() +
            ", searchType=" + getSearchType() +
            ", searchResultType=" + getSearchResultType() +
            "}";
    }
}
