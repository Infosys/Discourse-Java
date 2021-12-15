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
 * A DTO for the {@link com.infy.domain.TagSearchData} entity.
 */
public class TagSearchDataDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long tagId;

    private String searchData;

    private String rawData;

    private String locale;

    private Integer version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagSearchDataDTO)) {
            return false;
        }

        return id != null && id.equals(((TagSearchDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagSearchDataDTO{" +
            "id=" + getId() +
            ", tagId=" + getTagId() +
            ", searchData='" + getSearchData() + "'" +
            ", rawData='" + getRawData() + "'" +
            ", locale='" + getLocale() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
