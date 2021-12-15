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
 * A DTO for the {@link com.infy.domain.WatchedWords} entity.
 */
public class WatchedWordsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String word;

    @NotNull
    private Integer action;

    private String replacement;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchedWordsDTO)) {
            return false;
        }

        return id != null && id.equals(((WatchedWordsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchedWordsDTO{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", action=" + getAction() +
            ", replacement='" + getReplacement() + "'" +
            "}";
    }
}
