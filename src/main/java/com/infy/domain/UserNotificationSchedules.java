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
 * A UserNotificationSchedules.
 */
@Entity
@Table(name = "user_notification_schedules")
public class UserNotificationSchedules extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "day_0_start_time", nullable = false)
    private Integer day0StartTime;

    @NotNull
    @Column(name = "day_0_end_time", nullable = false)
    private Integer day0EndTime;

    @NotNull
    @Column(name = "day_1_start_time", nullable = false)
    private Integer day1StartTime;

    @NotNull
    @Column(name = "day_1_end_time", nullable = false)
    private Integer day1EndTime;

    @NotNull
    @Column(name = "day_2_start_time", nullable = false)
    private Integer day2StartTime;

    @NotNull
    @Column(name = "day_2_end_time", nullable = false)
    private Integer day2EndTime;

    @NotNull
    @Column(name = "day_3_start_time", nullable = false)
    private Integer day3StartTime;

    @NotNull
    @Column(name = "day_3_end_time", nullable = false)
    private Integer day3EndTime;

    @NotNull
    @Column(name = "day_4_start_time", nullable = false)
    private Integer day4StartTime;

    @NotNull
    @Column(name = "day_4_end_time", nullable = false)
    private Integer day4EndTime;

    @NotNull
    @Column(name = "day_5_start_time", nullable = false)
    private Integer day5StartTime;

    @NotNull
    @Column(name = "day_5_end_time", nullable = false)
    private Integer day5EndTime;

    @NotNull
    @Column(name = "day_6_start_time", nullable = false)
    private Integer day6StartTime;

    @NotNull
    @Column(name = "day_6_end_time", nullable = false)
    private Integer day6EndTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserNotificationSchedules userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public UserNotificationSchedules enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getDay0StartTime() {
        return day0StartTime;
    }

    public UserNotificationSchedules day0StartTime(Integer day0StartTime) {
        this.day0StartTime = day0StartTime;
        return this;
    }

    public void setDay0StartTime(Integer day0StartTime) {
        this.day0StartTime = day0StartTime;
    }

    public Integer getDay0EndTime() {
        return day0EndTime;
    }

    public UserNotificationSchedules day0EndTime(Integer day0EndTime) {
        this.day0EndTime = day0EndTime;
        return this;
    }

    public void setDay0EndTime(Integer day0EndTime) {
        this.day0EndTime = day0EndTime;
    }

    public Integer getDay1StartTime() {
        return day1StartTime;
    }

    public UserNotificationSchedules day1StartTime(Integer day1StartTime) {
        this.day1StartTime = day1StartTime;
        return this;
    }

    public void setDay1StartTime(Integer day1StartTime) {
        this.day1StartTime = day1StartTime;
    }

    public Integer getDay1EndTime() {
        return day1EndTime;
    }

    public UserNotificationSchedules day1EndTime(Integer day1EndTime) {
        this.day1EndTime = day1EndTime;
        return this;
    }

    public void setDay1EndTime(Integer day1EndTime) {
        this.day1EndTime = day1EndTime;
    }

    public Integer getDay2StartTime() {
        return day2StartTime;
    }

    public UserNotificationSchedules day2StartTime(Integer day2StartTime) {
        this.day2StartTime = day2StartTime;
        return this;
    }

    public void setDay2StartTime(Integer day2StartTime) {
        this.day2StartTime = day2StartTime;
    }

    public Integer getDay2EndTime() {
        return day2EndTime;
    }

    public UserNotificationSchedules day2EndTime(Integer day2EndTime) {
        this.day2EndTime = day2EndTime;
        return this;
    }

    public void setDay2EndTime(Integer day2EndTime) {
        this.day2EndTime = day2EndTime;
    }

    public Integer getDay3StartTime() {
        return day3StartTime;
    }

    public UserNotificationSchedules day3StartTime(Integer day3StartTime) {
        this.day3StartTime = day3StartTime;
        return this;
    }

    public void setDay3StartTime(Integer day3StartTime) {
        this.day3StartTime = day3StartTime;
    }

    public Integer getDay3EndTime() {
        return day3EndTime;
    }

    public UserNotificationSchedules day3EndTime(Integer day3EndTime) {
        this.day3EndTime = day3EndTime;
        return this;
    }

    public void setDay3EndTime(Integer day3EndTime) {
        this.day3EndTime = day3EndTime;
    }

    public Integer getDay4StartTime() {
        return day4StartTime;
    }

    public UserNotificationSchedules day4StartTime(Integer day4StartTime) {
        this.day4StartTime = day4StartTime;
        return this;
    }

    public void setDay4StartTime(Integer day4StartTime) {
        this.day4StartTime = day4StartTime;
    }

    public Integer getDay4EndTime() {
        return day4EndTime;
    }

    public UserNotificationSchedules day4EndTime(Integer day4EndTime) {
        this.day4EndTime = day4EndTime;
        return this;
    }

    public void setDay4EndTime(Integer day4EndTime) {
        this.day4EndTime = day4EndTime;
    }

    public Integer getDay5StartTime() {
        return day5StartTime;
    }

    public UserNotificationSchedules day5StartTime(Integer day5StartTime) {
        this.day5StartTime = day5StartTime;
        return this;
    }

    public void setDay5StartTime(Integer day5StartTime) {
        this.day5StartTime = day5StartTime;
    }

    public Integer getDay5EndTime() {
        return day5EndTime;
    }

    public UserNotificationSchedules day5EndTime(Integer day5EndTime) {
        this.day5EndTime = day5EndTime;
        return this;
    }

    public void setDay5EndTime(Integer day5EndTime) {
        this.day5EndTime = day5EndTime;
    }

    public Integer getDay6StartTime() {
        return day6StartTime;
    }

    public UserNotificationSchedules day6StartTime(Integer day6StartTime) {
        this.day6StartTime = day6StartTime;
        return this;
    }

    public void setDay6StartTime(Integer day6StartTime) {
        this.day6StartTime = day6StartTime;
    }

    public Integer getDay6EndTime() {
        return day6EndTime;
    }

    public UserNotificationSchedules day6EndTime(Integer day6EndTime) {
        this.day6EndTime = day6EndTime;
        return this;
    }

    public void setDay6EndTime(Integer day6EndTime) {
        this.day6EndTime = day6EndTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserNotificationSchedules)) {
            return false;
        }
        return id != null && id.equals(((UserNotificationSchedules) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserNotificationSchedules{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", day0StartTime=" + getDay0StartTime() +
            ", day0EndTime=" + getDay0EndTime() +
            ", day1StartTime=" + getDay1StartTime() +
            ", day1EndTime=" + getDay1EndTime() +
            ", day2StartTime=" + getDay2StartTime() +
            ", day2EndTime=" + getDay2EndTime() +
            ", day3StartTime=" + getDay3StartTime() +
            ", day3EndTime=" + getDay3EndTime() +
            ", day4StartTime=" + getDay4StartTime() +
            ", day4EndTime=" + getDay4EndTime() +
            ", day5StartTime=" + getDay5StartTime() +
            ", day5EndTime=" + getDay5EndTime() +
            ", day6StartTime=" + getDay6StartTime() +
            ", day6EndTime=" + getDay6EndTime() +
            "}";
    }
}
