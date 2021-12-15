/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.SchedulerStats} entity.
 */
public class SchedulerStatsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String hostname;

    @NotNull
    private Long pid;

    private Integer durationMs;

    private Integer liveSlotsStart;

    private Integer liveSlotsFinish;

    @NotNull
    private Instant startedAt;

    private Boolean success;

    private String error;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }

    public Integer getLiveSlotsStart() {
        return liveSlotsStart;
    }

    public void setLiveSlotsStart(Integer liveSlotsStart) {
        this.liveSlotsStart = liveSlotsStart;
    }

    public Integer getLiveSlotsFinish() {
        return liveSlotsFinish;
    }

    public void setLiveSlotsFinish(Integer liveSlotsFinish) {
        this.liveSlotsFinish = liveSlotsFinish;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchedulerStatsDTO)) {
            return false;
        }

        return id != null && id.equals(((SchedulerStatsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchedulerStatsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", pid=" + getPid() +
            ", durationMs=" + getDurationMs() +
            ", liveSlotsStart=" + getLiveSlotsStart() +
            ", liveSlotsFinish=" + getLiveSlotsFinish() +
            ", startedAt='" + getStartedAt() + "'" +
            ", success='" + isSuccess() + "'" +
            ", error='" + getError() + "'" +
            "}";
    }
}
