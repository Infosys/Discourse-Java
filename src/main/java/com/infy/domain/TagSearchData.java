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
 * A TagSearchData.
 */
@Entity
@Table(name = "tag_search_data")
public class TagSearchData extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "search_data")
    private String searchData;

    @Column(name = "raw_data")
    private String rawData;

    @Column(name = "locale")
    private String locale;

    @Column(name = "version")
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public TagSearchData tagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getSearchData() {
        return searchData;
    }

    public TagSearchData searchData(String searchData) {
        this.searchData = searchData;
        return this;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public String getRawData() {
        return rawData;
    }

    public TagSearchData rawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getLocale() {
        return locale;
    }

    public TagSearchData locale(String locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getVersion() {
        return version;
    }

    public TagSearchData version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagSearchData)) {
            return false;
        }
        return id != null && id.equals(((TagSearchData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagSearchData{" +
            "id=" + getId() +
            ", tagId=" + getTagId() +
            ", searchData='" + getSearchData() + "'" +
            ", rawData='" + getRawData() + "'" +
            ", locale='" + getLocale() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
