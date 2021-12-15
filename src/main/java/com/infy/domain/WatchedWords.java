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
 * A WatchedWords.
 */
@Entity
@Table(name = "watched_words")
public class WatchedWords extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "word", nullable = false)
    private String word;

    @NotNull
    @Column(name = "action", nullable = false)
    private Integer action;

    @Column(name = "replacement")
    private String replacement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public WatchedWords word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getAction() {
        return action;
    }

    public WatchedWords action(Integer action) {
        this.action = action;
        return this;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getReplacement() {
        return replacement;
    }

    public WatchedWords replacement(String replacement) {
        this.replacement = replacement;
        return this;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchedWords)) {
            return false;
        }
        return id != null && id.equals(((WatchedWords) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchedWords{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", action=" + getAction() +
            ", replacement='" + getReplacement() + "'" +
            "}";
    }
}
