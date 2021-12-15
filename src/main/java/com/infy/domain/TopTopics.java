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
 * A TopTopics.
 */
@Entity
@Table(name = "top_topics")
public class TopTopics extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "topic_id")
    private Long topicId;

    @NotNull
    @Column(name = "yearly_posts_count", nullable = false)
    private Integer yearlyPostsCount;

    @NotNull
    @Column(name = "yearly_views_count", nullable = false)
    private Integer yearlyViewsCount;

    @NotNull
    @Column(name = "yearly_likes_count", nullable = false)
    private Integer yearlyLikesCount;

    @NotNull
    @Column(name = "monthly_posts_count", nullable = false)
    private Integer monthlyPostsCount;

    @NotNull
    @Column(name = "monthly_views_count", nullable = false)
    private Integer monthlyViewsCount;

    @NotNull
    @Column(name = "monthly_likes_count", nullable = false)
    private Integer monthlyLikesCount;

    @NotNull
    @Column(name = "weekly_posts_count", nullable = false)
    private Integer weeklyPostsCount;

    @NotNull
    @Column(name = "weekly_views_count", nullable = false)
    private Integer weeklyViewsCount;

    @NotNull
    @Column(name = "weekly_likes_count", nullable = false)
    private Integer weeklyLikesCount;

    @NotNull
    @Column(name = "daily_posts_count", nullable = false)
    private Integer dailyPostsCount;

    @NotNull
    @Column(name = "daily_views_count", nullable = false)
    private Integer dailyViewsCount;

    @NotNull
    @Column(name = "daily_likes_count", nullable = false)
    private Integer dailyLikesCount;

    @Column(name = "daily_score")
    private Double dailyScore;

    @Column(name = "weekly_score")
    private Double weeklyScore;

    @Column(name = "monthly_score")
    private Double monthlyScore;

    @Column(name = "yearly_score")
    private Double yearlyScore;

    @Column(name = "all_score")
    private Double allScore;

    @NotNull
    @Column(name = "daily_op_likes_count", nullable = false)
    private Integer dailyOpLikesCount;

    @NotNull
    @Column(name = "weekly_op_likes_count", nullable = false)
    private Integer weeklyOpLikesCount;

    @NotNull
    @Column(name = "monthly_op_likes_count", nullable = false)
    private Integer monthlyOpLikesCount;

    @NotNull
    @Column(name = "yearly_op_likes_count", nullable = false)
    private Integer yearlyOpLikesCount;

    @NotNull
    @Column(name = "quarterly_posts_count", nullable = false)
    private Integer quarterlyPostsCount;

    @NotNull
    @Column(name = "quarterly_views_count", nullable = false)
    private Integer quarterlyViewsCount;

    @NotNull
    @Column(name = "quarterly_likes_count", nullable = false)
    private Integer quarterlyLikesCount;

    @Column(name = "quarterly_score")
    private Double quarterlyScore;

    @NotNull
    @Column(name = "quarterly_op_likes_count", nullable = false)
    private Integer quarterlyOpLikesCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public TopTopics topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getYearlyPostsCount() {
        return yearlyPostsCount;
    }

    public TopTopics yearlyPostsCount(Integer yearlyPostsCount) {
        this.yearlyPostsCount = yearlyPostsCount;
        return this;
    }

    public void setYearlyPostsCount(Integer yearlyPostsCount) {
        this.yearlyPostsCount = yearlyPostsCount;
    }

    public Integer getYearlyViewsCount() {
        return yearlyViewsCount;
    }

    public TopTopics yearlyViewsCount(Integer yearlyViewsCount) {
        this.yearlyViewsCount = yearlyViewsCount;
        return this;
    }

    public void setYearlyViewsCount(Integer yearlyViewsCount) {
        this.yearlyViewsCount = yearlyViewsCount;
    }

    public Integer getYearlyLikesCount() {
        return yearlyLikesCount;
    }

    public TopTopics yearlyLikesCount(Integer yearlyLikesCount) {
        this.yearlyLikesCount = yearlyLikesCount;
        return this;
    }

    public void setYearlyLikesCount(Integer yearlyLikesCount) {
        this.yearlyLikesCount = yearlyLikesCount;
    }

    public Integer getMonthlyPostsCount() {
        return monthlyPostsCount;
    }

    public TopTopics monthlyPostsCount(Integer monthlyPostsCount) {
        this.monthlyPostsCount = monthlyPostsCount;
        return this;
    }

    public void setMonthlyPostsCount(Integer monthlyPostsCount) {
        this.monthlyPostsCount = monthlyPostsCount;
    }

    public Integer getMonthlyViewsCount() {
        return monthlyViewsCount;
    }

    public TopTopics monthlyViewsCount(Integer monthlyViewsCount) {
        this.monthlyViewsCount = monthlyViewsCount;
        return this;
    }

    public void setMonthlyViewsCount(Integer monthlyViewsCount) {
        this.monthlyViewsCount = monthlyViewsCount;
    }

    public Integer getMonthlyLikesCount() {
        return monthlyLikesCount;
    }

    public TopTopics monthlyLikesCount(Integer monthlyLikesCount) {
        this.monthlyLikesCount = monthlyLikesCount;
        return this;
    }

    public void setMonthlyLikesCount(Integer monthlyLikesCount) {
        this.monthlyLikesCount = monthlyLikesCount;
    }

    public Integer getWeeklyPostsCount() {
        return weeklyPostsCount;
    }

    public TopTopics weeklyPostsCount(Integer weeklyPostsCount) {
        this.weeklyPostsCount = weeklyPostsCount;
        return this;
    }

    public void setWeeklyPostsCount(Integer weeklyPostsCount) {
        this.weeklyPostsCount = weeklyPostsCount;
    }

    public Integer getWeeklyViewsCount() {
        return weeklyViewsCount;
    }

    public TopTopics weeklyViewsCount(Integer weeklyViewsCount) {
        this.weeklyViewsCount = weeklyViewsCount;
        return this;
    }

    public void setWeeklyViewsCount(Integer weeklyViewsCount) {
        this.weeklyViewsCount = weeklyViewsCount;
    }

    public Integer getWeeklyLikesCount() {
        return weeklyLikesCount;
    }

    public TopTopics weeklyLikesCount(Integer weeklyLikesCount) {
        this.weeklyLikesCount = weeklyLikesCount;
        return this;
    }

    public void setWeeklyLikesCount(Integer weeklyLikesCount) {
        this.weeklyLikesCount = weeklyLikesCount;
    }

    public Integer getDailyPostsCount() {
        return dailyPostsCount;
    }

    public TopTopics dailyPostsCount(Integer dailyPostsCount) {
        this.dailyPostsCount = dailyPostsCount;
        return this;
    }

    public void setDailyPostsCount(Integer dailyPostsCount) {
        this.dailyPostsCount = dailyPostsCount;
    }

    public Integer getDailyViewsCount() {
        return dailyViewsCount;
    }

    public TopTopics dailyViewsCount(Integer dailyViewsCount) {
        this.dailyViewsCount = dailyViewsCount;
        return this;
    }

    public void setDailyViewsCount(Integer dailyViewsCount) {
        this.dailyViewsCount = dailyViewsCount;
    }

    public Integer getDailyLikesCount() {
        return dailyLikesCount;
    }

    public TopTopics dailyLikesCount(Integer dailyLikesCount) {
        this.dailyLikesCount = dailyLikesCount;
        return this;
    }

    public void setDailyLikesCount(Integer dailyLikesCount) {
        this.dailyLikesCount = dailyLikesCount;
    }

    public Double getDailyScore() {
        return dailyScore;
    }

    public TopTopics dailyScore(Double dailyScore) {
        this.dailyScore = dailyScore;
        return this;
    }

    public void setDailyScore(Double dailyScore) {
        this.dailyScore = dailyScore;
    }

    public Double getWeeklyScore() {
        return weeklyScore;
    }

    public TopTopics weeklyScore(Double weeklyScore) {
        this.weeklyScore = weeklyScore;
        return this;
    }

    public void setWeeklyScore(Double weeklyScore) {
        this.weeklyScore = weeklyScore;
    }

    public Double getMonthlyScore() {
        return monthlyScore;
    }

    public TopTopics monthlyScore(Double monthlyScore) {
        this.monthlyScore = monthlyScore;
        return this;
    }

    public void setMonthlyScore(Double monthlyScore) {
        this.monthlyScore = monthlyScore;
    }

    public Double getYearlyScore() {
        return yearlyScore;
    }

    public TopTopics yearlyScore(Double yearlyScore) {
        this.yearlyScore = yearlyScore;
        return this;
    }

    public void setYearlyScore(Double yearlyScore) {
        this.yearlyScore = yearlyScore;
    }

    public Double getAllScore() {
        return allScore;
    }

    public TopTopics allScore(Double allScore) {
        this.allScore = allScore;
        return this;
    }

    public void setAllScore(Double allScore) {
        this.allScore = allScore;
    }

    public Integer getDailyOpLikesCount() {
        return dailyOpLikesCount;
    }

    public TopTopics dailyOpLikesCount(Integer dailyOpLikesCount) {
        this.dailyOpLikesCount = dailyOpLikesCount;
        return this;
    }

    public void setDailyOpLikesCount(Integer dailyOpLikesCount) {
        this.dailyOpLikesCount = dailyOpLikesCount;
    }

    public Integer getWeeklyOpLikesCount() {
        return weeklyOpLikesCount;
    }

    public TopTopics weeklyOpLikesCount(Integer weeklyOpLikesCount) {
        this.weeklyOpLikesCount = weeklyOpLikesCount;
        return this;
    }

    public void setWeeklyOpLikesCount(Integer weeklyOpLikesCount) {
        this.weeklyOpLikesCount = weeklyOpLikesCount;
    }

    public Integer getMonthlyOpLikesCount() {
        return monthlyOpLikesCount;
    }

    public TopTopics monthlyOpLikesCount(Integer monthlyOpLikesCount) {
        this.monthlyOpLikesCount = monthlyOpLikesCount;
        return this;
    }

    public void setMonthlyOpLikesCount(Integer monthlyOpLikesCount) {
        this.monthlyOpLikesCount = monthlyOpLikesCount;
    }

    public Integer getYearlyOpLikesCount() {
        return yearlyOpLikesCount;
    }

    public TopTopics yearlyOpLikesCount(Integer yearlyOpLikesCount) {
        this.yearlyOpLikesCount = yearlyOpLikesCount;
        return this;
    }

    public void setYearlyOpLikesCount(Integer yearlyOpLikesCount) {
        this.yearlyOpLikesCount = yearlyOpLikesCount;
    }

    public Integer getQuarterlyPostsCount() {
        return quarterlyPostsCount;
    }

    public TopTopics quarterlyPostsCount(Integer quarterlyPostsCount) {
        this.quarterlyPostsCount = quarterlyPostsCount;
        return this;
    }

    public void setQuarterlyPostsCount(Integer quarterlyPostsCount) {
        this.quarterlyPostsCount = quarterlyPostsCount;
    }

    public Integer getQuarterlyViewsCount() {
        return quarterlyViewsCount;
    }

    public TopTopics quarterlyViewsCount(Integer quarterlyViewsCount) {
        this.quarterlyViewsCount = quarterlyViewsCount;
        return this;
    }

    public void setQuarterlyViewsCount(Integer quarterlyViewsCount) {
        this.quarterlyViewsCount = quarterlyViewsCount;
    }

    public Integer getQuarterlyLikesCount() {
        return quarterlyLikesCount;
    }

    public TopTopics quarterlyLikesCount(Integer quarterlyLikesCount) {
        this.quarterlyLikesCount = quarterlyLikesCount;
        return this;
    }

    public void setQuarterlyLikesCount(Integer quarterlyLikesCount) {
        this.quarterlyLikesCount = quarterlyLikesCount;
    }

    public Double getQuarterlyScore() {
        return quarterlyScore;
    }

    public TopTopics quarterlyScore(Double quarterlyScore) {
        this.quarterlyScore = quarterlyScore;
        return this;
    }

    public void setQuarterlyScore(Double quarterlyScore) {
        this.quarterlyScore = quarterlyScore;
    }

    public Integer getQuarterlyOpLikesCount() {
        return quarterlyOpLikesCount;
    }

    public TopTopics quarterlyOpLikesCount(Integer quarterlyOpLikesCount) {
        this.quarterlyOpLikesCount = quarterlyOpLikesCount;
        return this;
    }

    public void setQuarterlyOpLikesCount(Integer quarterlyOpLikesCount) {
        this.quarterlyOpLikesCount = quarterlyOpLikesCount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopTopics)) {
            return false;
        }
        return id != null && id.equals(((TopTopics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopTopics{" +
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
