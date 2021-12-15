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
 * A DTO for the {@link com.infy.domain.TranslationOverrides} entity.
 */
public class TranslationOverridesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String locale;

    @NotNull
    private String translationKey;

    @NotNull
    private String value;

    private String compiledJs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCompiledJs() {
        return compiledJs;
    }

    public void setCompiledJs(String compiledJs) {
        this.compiledJs = compiledJs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranslationOverridesDTO)) {
            return false;
        }

        return id != null && id.equals(((TranslationOverridesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranslationOverridesDTO{" +
            "id=" + getId() +
            ", locale='" + getLocale() + "'" +
            ", translationKey='" + getTranslationKey() + "'" +
            ", value='" + getValue() + "'" +
            ", compiledJs='" + getCompiledJs() + "'" +
            "}";
    }
}
