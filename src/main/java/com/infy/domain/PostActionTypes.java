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
 * A PostActionTypes.
 */
@Entity
@Table(name = "post_action_types")
public class PostActionTypes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name_key", nullable = false)
    private String nameKey;

    @NotNull
    @Column(name = "is_flag", nullable = false)
    private Boolean isFlag;

    @Column(name = "icon")
    private String icon;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @NotNull
    @Column(name = "score_bonus", nullable = false)
    private Double scoreBonus;

    @NotNull
    @Column(name = "reviewable_priority", nullable = false)
    private Integer reviewablePriority;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameKey() {
        return nameKey;
    }

    public PostActionTypes nameKey(String nameKey) {
        this.nameKey = nameKey;
        return this;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public Boolean isIsFlag() {
        return isFlag;
    }

    public PostActionTypes isFlag(Boolean isFlag) {
        this.isFlag = isFlag;
        return this;
    }

    public void setIsFlag(Boolean isFlag) {
        this.isFlag = isFlag;
    }

    public String getIcon() {
        return icon;
    }

    public PostActionTypes icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPosition() {
        return position;
    }

    public PostActionTypes position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getScoreBonus() {
        return scoreBonus;
    }

    public PostActionTypes scoreBonus(Double scoreBonus) {
        this.scoreBonus = scoreBonus;
        return this;
    }

    public void setScoreBonus(Double scoreBonus) {
        this.scoreBonus = scoreBonus;
    }

    public Integer getReviewablePriority() {
        return reviewablePriority;
    }

    public PostActionTypes reviewablePriority(Integer reviewablePriority) {
        this.reviewablePriority = reviewablePriority;
        return this;
    }

    public void setReviewablePriority(Integer reviewablePriority) {
        this.reviewablePriority = reviewablePriority;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostActionTypes)) {
            return false;
        }
        return id != null && id.equals(((PostActionTypes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostActionTypes{" +
            "id=" + getId() +
            ", nameKey='" + getNameKey() + "'" +
            ", isFlag='" + isIsFlag() + "'" +
            ", icon='" + getIcon() + "'" +
            ", position=" + getPosition() +
            ", scoreBonus=" + getScoreBonus() +
            ", reviewablePriority=" + getReviewablePriority() +
            "}";
    }
}
