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
 * A DTO for the {@link com.infy.domain.PostSearchData} entity.
 */
public class PostSearchDataDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long postId;

    private String searchData;

    private String rawData;

    private String locale;

    private Integer version;

    @NotNull
    private Boolean privateMessage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public Boolean isPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(Boolean privateMessage) {
        this.privateMessage = privateMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostSearchDataDTO)) {
            return false;
        }

        return id != null && id.equals(((PostSearchDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostSearchDataDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", searchData='" + getSearchData() + "'" +
            ", rawData='" + getRawData() + "'" +
            ", locale='" + getLocale() + "'" +
            ", version=" + getVersion() +
            ", privateMessage='" + isPrivateMessage() + "'" +
            "}";
    }
}
