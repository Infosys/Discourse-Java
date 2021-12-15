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
 * A DTO for the {@link com.infy.domain.TopTopics} entity.
 */
public class TopTopicsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long topicId;

    @NotNull
    private Integer yearlyPostsCount;

    @NotNull
    private Integer yearlyViewsCount;

    @NotNull
    private Integer yearlyLikesCount;

    @NotNull
    private Integer monthlyPostsCount;

    @NotNull
    private Integer monthlyViewsCount;

    @NotNull
    private Integer monthlyLikesCount;

    @NotNull
    private Integer weeklyPostsCount;

    @NotNull
    private Integer weeklyViewsCount;

    @NotNull
    private Integer weeklyLikesCount;

    @NotNull
    private Integer dailyPostsCount;

    @NotNull
    private Integer dailyViewsCount;

    @NotNull
    private Integer dailyLikesCount;

    private Double dailyScore;

    private Double weeklyScore;

    private Double monthlyScore;

    private Double yearlyScore;

    private Double allScore;

    @NotNull
    private Integer dailyOpLikesCount;

    @NotNull
    private Integer weeklyOpLikesCount;

    @NotNull
    private Integer monthlyOpLikesCount;

    @NotNull
    private Integer yearlyOpLikesCount;

    @NotNull
    private Integer quarterlyPostsCount;

    @NotNull
    private Integer quarterlyViewsCount;

    @NotNull
    private Integer quarterlyLikesCount;

    private Double quarterlyScore;

    @NotNull
    private Integer quarterlyOpLikesCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getYearlyPostsCount() {
        return yearlyPostsCount;
    }

    public void setYearlyPostsCount(Integer yearlyPostsCount) {
        this.yearlyPostsCount = yearlyPostsCount;
    }

    public Integer getYearlyViewsCount() {
        return yearlyViewsCount;
    }

    public void setYearlyViewsCount(Integer yearlyViewsCount) {
        this.yearlyViewsCount = yearlyViewsCount;
    }

    public Integer getYearlyLikesCount() {
        return yearlyLikesCount;
    }

    public void setYearlyLikesCount(Integer yearlyLikesCount) {
        this.yearlyLikesCount = yearlyLikesCount;
    }

    public Integer getMonthlyPostsCount() {
        return monthlyPostsCount;
    }

    public void setMonthlyPostsCount(Integer monthlyPostsCount) {
        this.monthlyPostsCount = monthlyPostsCount;
    }

    public Integer getMonthlyViewsCount() {
        return monthlyViewsCount;
    }

    public void setMonthlyViewsCount(Integer monthlyViewsCount) {
        this.monthlyViewsCount = monthlyViewsCount;
    }

    public Integer getMonthlyLikesCount() {
        return monthlyLikesCount;
    }

    public void setMonthlyLikesCount(Integer monthlyLikesCount) {
        this.monthlyLikesCount = monthlyLikesCount;
    }

    public Integer getWeeklyPostsCount() {
        return weeklyPostsCount;
    }

    public void setWeeklyPostsCount(Integer weeklyPostsCount) {
        this.weeklyPostsCount = weeklyPostsCount;
    }

    public Integer getWeeklyViewsCount() {
        return weeklyViewsCount;
    }

    public void setWeeklyViewsCount(Integer weeklyViewsCount) {
        this.weeklyViewsCount = weeklyViewsCount;
    }

    public Integer getWeeklyLikesCount() {
        return weeklyLikesCount;
    }

    public void setWeeklyLikesCount(Integer weeklyLikesCount) {
        this.weeklyLikesCount = weeklyLikesCount;
    }

    public Integer getDailyPostsCount() {
        return dailyPostsCount;
    }

    public void setDailyPostsCount(Integer dailyPostsCount) {
        this.dailyPostsCount = dailyPostsCount;
    }

    public Integer getDailyViewsCount() {
        return dailyViewsCount;
    }

    public void setDailyViewsCount(Integer dailyViewsCount) {
        this.dailyViewsCount = dailyViewsCount;
    }

    public Integer getDailyLikesCount() {
        return dailyLikesCount;
    }

    public void setDailyLikesCount(Integer dailyLikesCount) {
        this.dailyLikesCount = dailyLikesCount;
    }

    public Double getDailyScore() {
        return dailyScore;
    }

    public void setDailyScore(Double dailyScore) {
        this.dailyScore = dailyScore;
    }

    public Double getWeeklyScore() {
        return weeklyScore;
    }

    public void setWeeklyScore(Double weeklyScore) {
        this.weeklyScore = weeklyScore;
    }

    public Double getMonthlyScore() {
        return monthlyScore;
    }

    public void setMonthlyScore(Double monthlyScore) {
        this.monthlyScore = monthlyScore;
    }

    public Double getYearlyScore() {
        return yearlyScore;
    }

    public void setYearlyScore(Double yearlyScore) {
        this.yearlyScore = yearlyScore;
    }

    public Double getAllScore() {
        return allScore;
    }

    public void setAllScore(Double allScore) {
        this.allScore = allScore;
    }

    public Integer getDailyOpLikesCount() {
        return dailyOpLikesCount;
    }

    public void setDailyOpLikesCount(Integer dailyOpLikesCount) {
        this.dailyOpLikesCount = dailyOpLikesCount;
    }

    public Integer getWeeklyOpLikesCount() {
        return weeklyOpLikesCount;
    }

    public void setWeeklyOpLikesCount(Integer weeklyOpLikesCount) {
        this.weeklyOpLikesCount = weeklyOpLikesCount;
    }

    public Integer getMonthlyOpLikesCount() {
        return monthlyOpLikesCount;
    }

    public void setMonthlyOpLikesCount(Integer monthlyOpLikesCount) {
        this.monthlyOpLikesCount = monthlyOpLikesCount;
    }

    public Integer getYearlyOpLikesCount() {
        return yearlyOpLikesCount;
    }

    public void setYearlyOpLikesCount(Integer yearlyOpLikesCount) {
        this.yearlyOpLikesCount = yearlyOpLikesCount;
    }

    public Integer getQuarterlyPostsCount() {
        return quarterlyPostsCount;
    }

    public void setQuarterlyPostsCount(Integer quarterlyPostsCount) {
        this.quarterlyPostsCount = quarterlyPostsCount;
    }

    public Integer getQuarterlyViewsCount() {
        return quarterlyViewsCount;
    }

    public void setQuarterlyViewsCount(Integer quarterlyViewsCount) {
        this.quarterlyViewsCount = quarterlyViewsCount;
    }

    public Integer getQuarterlyLikesCount() {
        return quarterlyLikesCount;
    }

    public void setQuarterlyLikesCount(Integer quarterlyLikesCount) {
        this.quarterlyLikesCount = quarterlyLikesCount;
    }

    public Double getQuarterlyScore() {
        return quarterlyScore;
    }

    public void setQuarterlyScore(Double quarterlyScore) {
        this.quarterlyScore = quarterlyScore;
    }

    public Integer getQuarterlyOpLikesCount() {
        return quarterlyOpLikesCount;
    }

    public void setQuarterlyOpLikesCount(Integer quarterlyOpLikesCount) {
        this.quarterlyOpLikesCount = quarterlyOpLikesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopTopicsDTO)) {
            return false;
        }

        return id != null && id.equals(((TopTopicsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopTopicsDTO{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", yearlyPostsCount=" + getYearlyPostsCount() +
            ", yearlyViewsCount=" + getYearlyViewsCount() +
            ", yearlyLikesCount=" + getYearlyLikesCount() +
            ", monthlyPostsCount=" + getMonthlyPostsCount() +
            ", monthlyViewsCount=" + getMonthlyViewsCount() +
            ", monthlyLikesCount=" + getMonthlyLikesCount() +
            ", weeklyPostsCount=" + getWeeklyPostsCount() +
            ", weeklyViewsCount=" + getWeeklyViewsCount() +
            ", weeklyLikesCount=" + getWeeklyLikesCount() +
            ", dailyPostsCount=" + getDailyPostsCount() +
            ", dailyViewsCount=" + getDailyViewsCount() +
            ", dailyLikesCount=" + getDailyLikesCount() +
            ", dailyScore=" + getDailyScore() +
            ", weeklyScore=" + getWeeklyScore() +
            ", monthlyScore=" + getMonthlyScore() +
            ", yearlyScore=" + getYearlyScore() +
            ", allScore=" + getAllScore() +
            ", dailyOpLikesCount=" + getDailyOpLikesCount() +
            ", weeklyOpLikesCount=" + getWeeklyOpLikesCount() +
            ", monthlyOpLikesCount=" + getMonthlyOpLikesCount() +
            ", yearlyOpLikesCount=" + getYearlyOpLikesCount() +
            ", quarterlyPostsCount=" + getQuarterlyPostsCount() +
            ", quarterlyViewsCount=" + getQuarterlyViewsCount() +
            ", quarterlyLikesCount=" + getQuarterlyLikesCount() +
            ", quarterlyScore=" + getQuarterlyScore() +
            ", quarterlyOpLikesCount=" + getQuarterlyOpLikesCount() +
            "}";
    }
}
