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
import java.time.Instant;

/**
 * A SchedulerStats.
 */
@Entity
@Table(name = "scheduler_stats")
public class SchedulerStats extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "hostname", nullable = false)
    private String hostname;

    @NotNull
    @Column(name = "pid", nullable = false)
    private Long pid;

    @Column(name = "duration_ms")
    private Integer durationMs;

    @Column(name = "live_slots_start")
    private Integer liveSlotsStart;

    @Column(name = "live_slots_finish")
    private Integer liveSlotsFinish;

    @NotNull
    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "error")
    private String error;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SchedulerStats name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public SchedulerStats hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getPid() {
        return pid;
    }

    public SchedulerStats pid(Long pid) {
        this.pid = pid;
        return this;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getDurationMs() {
        return durationMs;
    }

    public SchedulerStats durationMs(Integer durationMs) {
        this.durationMs = durationMs;
        return this;
    }

    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }

    public Integer getLiveSlotsStart() {
        return liveSlotsStart;
    }

    public SchedulerStats liveSlotsStart(Integer liveSlotsStart) {
        this.liveSlotsStart = liveSlotsStart;
        return this;
    }

    public void setLiveSlotsStart(Integer liveSlotsStart) {
        this.liveSlotsStart = liveSlotsStart;
    }

    public Integer getLiveSlotsFinish() {
        return liveSlotsFinish;
    }

    public SchedulerStats liveSlotsFinish(Integer liveSlotsFinish) {
        this.liveSlotsFinish = liveSlotsFinish;
        return this;
    }

    public void setLiveSlotsFinish(Integer liveSlotsFinish) {
        this.liveSlotsFinish = liveSlotsFinish;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public SchedulerStats startedAt(Instant startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Boolean isSuccess() {
        return success;
    }

    public SchedulerStats success(Boolean success) {
        this.success = success;
        return this;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public SchedulerStats error(String error) {
        this.error = error;
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchedulerStats)) {
            return false;
        }
        return id != null && id.equals(((SchedulerStats) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchedulerStats{" +
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
