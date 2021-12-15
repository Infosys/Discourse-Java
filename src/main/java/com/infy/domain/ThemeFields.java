/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ThemeFields.
 */
@Entity
@Table(name = "theme_fields")
public class ThemeFields extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "theme_id", nullable = false)
    private Long themeId;

    @NotNull
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "value_baked")
    private String valueBaked;

    @NotNull
    @Column(name = "compiler_version", nullable = false)
    private String compilerVersion;

    @Column(name = "error")
    private String error;

    @Column(name = "upload_id")
    private Long uploadId;

    @NotNull
    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @ManyToOne
    @JsonIgnoreProperties(value = "themeFields", allowSetters = true)
    private JavascriptCaches javascriptCaches;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public ThemeFields themeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public ThemeFields targetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getName() {
        return name;
    }

    public ThemeFields name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public ThemeFields value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueBaked() {
        return valueBaked;
    }

    public ThemeFields valueBaked(String valueBaked) {
        this.valueBaked = valueBaked;
        return this;
    }

    public void setValueBaked(String valueBaked) {
        this.valueBaked = valueBaked;
    }

    public String getCompilerVersion() {
        return compilerVersion;
    }

    public ThemeFields compilerVersion(String compilerVersion) {
        this.compilerVersion = compilerVersion;
        return this;
    }

    public void setCompilerVersion(String compilerVersion) {
        this.compilerVersion = compilerVersion;
    }

    public String getError() {
        return error;
    }

    public ThemeFields error(String error) {
        this.error = error;
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public ThemeFields uploadId(Long uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public ThemeFields typeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public JavascriptCaches getJavascriptCaches() {
        return javascriptCaches;
    }

    public ThemeFields javascriptCaches(JavascriptCaches javascriptCaches) {
        this.javascriptCaches = javascriptCaches;
        return this;
    }

    public void setJavascriptCaches(JavascriptCaches javascriptCaches) {
        this.javascriptCaches = javascriptCaches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeFields)) {
            return false;
        }
        return id != null && id.equals(((ThemeFields) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeFields{" +
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
            "}";
    }
}
