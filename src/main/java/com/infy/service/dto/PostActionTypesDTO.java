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
 * A DTO for the {@link com.infy.domain.PostActionTypes} entity.
 */
public class PostActionTypesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameKey;

    @NotNull
    private Boolean isFlag;

    private String icon;

    @NotNull
    private Integer position;

    @NotNull
    private Double scoreBonus;

    @NotNull
    private Integer reviewablePriority;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public Boolean isIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Boolean isFlag) {
        this.isFlag = isFlag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getScoreBonus() {
        return scoreBonus;
    }

    public void setScoreBonus(Double scoreBonus) {
        this.scoreBonus = scoreBonus;
    }

    public Integer getReviewablePriority() {
        return reviewablePriority;
    }

    public void setReviewablePriority(Integer reviewablePriority) {
        this.reviewablePriority = reviewablePriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostActionTypesDTO)) {
            return false;
        }

        return id != null && id.equals(((PostActionTypesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostActionTypesDTO{" +
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
