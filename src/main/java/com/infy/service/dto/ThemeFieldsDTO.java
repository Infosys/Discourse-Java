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
 * A DTO for the {@link com.infy.domain.ThemeFields} entity.
 */
public class ThemeFieldsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long themeId;

    @NotNull
    private Long targetId;

    @NotNull
    private String name;

    @NotNull
    private String value;

    private String valueBaked;

    @NotNull
    private String compilerVersion;

    private String error;

    private Long uploadId;

    @NotNull
    private Long typeId;


    private Long javascriptCachesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueBaked() {
        return valueBaked;
    }

    public void setValueBaked(String valueBaked) {
        this.valueBaked = valueBaked;
    }

    public String getCompilerVersion() {
        return compilerVersion;
    }

    public void setCompilerVersion(String compilerVersion) {
        this.compilerVersion = compilerVersion;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getJavascriptCachesId() {
        return javascriptCachesId;
    }

    public void setJavascriptCachesId(Long javascriptCachesId) {
        this.javascriptCachesId = javascriptCachesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeFieldsDTO)) {
            return false;
        }

        return id != null && id.equals(((ThemeFieldsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeFieldsDTO{" +
            "id=" + getId() +
            ", themeId=" + getThemeId() +
            ", targetId=" + getTargetId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueBaked='" + getValueBaked() + "'" +
            ", compilerVersion='" + getCompilerVersion() + "'" +
            ", error='" + getError() + "'" +
            ", uploadId=" + getUploadId() +
            ", typeId=" + getTypeId() +
            ", javascriptCachesId=" + getJavascriptCachesId() +
            "}";
    }
}
