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
 * A DTO for the {@link com.infy.domain.Polls} entity.
 */
public class PollsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long postId;

    @NotNull
    private String name;

    private Instant closeAt;

    @NotNull
    private Integer type;

    @NotNull
    private Integer status;

    @NotNull
    private Integer results;

    @NotNull
    private Integer visibility;

    private Integer min;

    private Integer max;

    private Integer step;

    private Integer anonymousVoters;

    @NotNull
    private Integer chartType;

    private String groups;

    private String title;


    private Long pollVotesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(Instant closeAt) {
        this.closeAt = closeAt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResults() {
        return results;
    }

    public void setResults(Integer results) {
        this.results = results;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getAnonymousVoters() {
        return anonymousVoters;
    }

    public void setAnonymousVoters(Integer anonymousVoters) {
        this.anonymousVoters = anonymousVoters;
    }

    public Integer getChartType() {
        return chartType;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPollVotesId() {
        return pollVotesId;
    }

    public void setPollVotesId(Long pollVotesId) {
        this.pollVotesId = pollVotesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollsDTO)) {
            return false;
        }

        return id != null && id.equals(((PollsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollsDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", name='" + getName() + "'" +
            ", closeAt='" + getCloseAt() + "'" +
            ", type=" + getType() +
            ", status=" + getStatus() +
            ", results=" + getResults() +
            ", visibility=" + getVisibility() +
            ", min=" + getMin() +
            ", max=" + getMax() +
            ", step=" + getStep() +
            ", anonymousVoters=" + getAnonymousVoters() +
            ", chartType=" + getChartType() +
            ", groups='" + getGroups() + "'" +
            ", title='" + getTitle() + "'" +
            ", pollVotesId=" + getPollVotesId() +
            "}";
    }
}
