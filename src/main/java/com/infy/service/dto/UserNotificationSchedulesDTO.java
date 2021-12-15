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
 * A DTO for the {@link com.infy.domain.UserNotificationSchedules} entity.
 */
public class UserNotificationSchedulesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Integer day0StartTime;

    @NotNull
    private Integer day0EndTime;

    @NotNull
    private Integer day1StartTime;

    @NotNull
    private Integer day1EndTime;

    @NotNull
    private Integer day2StartTime;

    @NotNull
    private Integer day2EndTime;

    @NotNull
    private Integer day3StartTime;

    @NotNull
    private Integer day3EndTime;

    @NotNull
    private Integer day4StartTime;

    @NotNull
    private Integer day4EndTime;

    @NotNull
    private Integer day5StartTime;

    @NotNull
    private Integer day5EndTime;

    @NotNull
    private Integer day6StartTime;

    @NotNull
    private Integer day6EndTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getDay0StartTime() {
        return day0StartTime;
    }

    public void setDay0StartTime(Integer day0StartTime) {
        this.day0StartTime = day0StartTime;
    }

    public Integer getDay0EndTime() {
        return day0EndTime;
    }

    public void setDay0EndTime(Integer day0EndTime) {
        this.day0EndTime = day0EndTime;
    }

    public Integer getDay1StartTime() {
        return day1StartTime;
    }

    public void setDay1StartTime(Integer day1StartTime) {
        this.day1StartTime = day1StartTime;
    }

    public Integer getDay1EndTime() {
        return day1EndTime;
    }

    public void setDay1EndTime(Integer day1EndTime) {
        this.day1EndTime = day1EndTime;
    }

    public Integer getDay2StartTime() {
        return day2StartTime;
    }

    public void setDay2StartTime(Integer day2StartTime) {
        this.day2StartTime = day2StartTime;
    }

    public Integer getDay2EndTime() {
        return day2EndTime;
    }

    public void setDay2EndTime(Integer day2EndTime) {
        this.day2EndTime = day2EndTime;
    }

    public Integer getDay3StartTime() {
        return day3StartTime;
    }

    public void setDay3StartTime(Integer day3StartTime) {
        this.day3StartTime = day3StartTime;
    }

    public Integer getDay3EndTime() {
        return day3EndTime;
    }

    public void setDay3EndTime(Integer day3EndTime) {
        this.day3EndTime = day3EndTime;
    }

    public Integer getDay4StartTime() {
        return day4StartTime;
    }

    public void setDay4StartTime(Integer day4StartTime) {
        this.day4StartTime = day4StartTime;
    }

    public Integer getDay4EndTime() {
        return day4EndTime;
    }

    public void setDay4EndTime(Integer day4EndTime) {
        this.day4EndTime = day4EndTime;
    }

    public Integer getDay5StartTime() {
        return day5StartTime;
    }

    public void setDay5StartTime(Integer day5StartTime) {
        this.day5StartTime = day5StartTime;
    }

    public Integer getDay5EndTime() {
        return day5EndTime;
    }

    public void setDay5EndTime(Integer day5EndTime) {
        this.day5EndTime = day5EndTime;
    }

    public Integer getDay6StartTime() {
        return day6StartTime;
    }

    public void setDay6StartTime(Integer day6StartTime) {
        this.day6StartTime = day6StartTime;
    }

    public Integer getDay6EndTime() {
        return day6EndTime;
    }

    public void setDay6EndTime(Integer day6EndTime) {
        this.day6EndTime = day6EndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserNotificationSchedulesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserNotificationSchedulesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserNotificationSchedulesDTO{" +
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
