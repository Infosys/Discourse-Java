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
 * A DTO for the {@link com.infy.domain.SearchLogs} entity.
 */
public class SearchLogsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String term;

    private String userId;

    private String ipAddress;

    private Long searchResultId;

    @NotNull
    private Integer searchType;

    private Integer searchResultType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getSearchResultId() {
        return searchResultId;
    }

    public void setSearchResultId(Long searchResultId) {
        this.searchResultId = searchResultId;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getSearchResultType() {
        return searchResultType;
    }

    public void setSearchResultType(Integer searchResultType) {
        this.searchResultType = searchResultType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((SearchLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SearchLogsDTO{" +
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
