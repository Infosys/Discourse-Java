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
 * A DTO for the {@link com.infy.domain.ThemeTranslationOverrides} entity.
 */
public class ThemeTranslationOverridesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long themeId;

    @NotNull
    private String locale;

    @NotNull
    private String translationKey;

    @NotNull
    private String value;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeTranslationOverridesDTO)) {
            return false;
        }

        return id != null && id.equals(((ThemeTranslationOverridesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeTranslationOverridesDTO{" +
            "id=" + getId() +
            ", themeId=" + getThemeId() +
            ", locale='" + getLocale() + "'" +
            ", translationKey='" + getTranslationKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
