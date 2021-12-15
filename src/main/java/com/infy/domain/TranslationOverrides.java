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
 * A TranslationOverrides.
 */
@Entity
@Table(name = "translation_overrides")
public class TranslationOverrides extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "locale", nullable = false)
    private String locale;

    @NotNull
    @Column(name = "translation_key", nullable = false)
    private String translationKey;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "compiled_js")
    private String compiledJs;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public TranslationOverrides locale(String locale) {
        this.locale = locale;
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public TranslationOverrides translationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getValue() {
        return value;
    }

    public TranslationOverrides value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCompiledJs() {
        return compiledJs;
    }

    public TranslationOverrides compiledJs(String compiledJs) {
        this.compiledJs = compiledJs;
        return this;
    }

    public void setCompiledJs(String compiledJs) {
        this.compiledJs = compiledJs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranslationOverrides)) {
            return false;
        }
        return id != null && id.equals(((TranslationOverrides) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranslationOverrides{" +
            "id=" + getId() +
            ", locale='" + getLocale() + "'" +
            ", translationKey='" + getTranslationKey() + "'" +
            ", value='" + getValue() + "'" +
            ", compiledJs='" + getCompiledJs() + "'" +
            "}";
    }
}
