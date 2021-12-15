/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Polls.
 */
@Entity
@Table(name = "polls")
public class Polls extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "close_at")
    private Instant closeAt;

    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "results", nullable = false)
    private Integer results;

    @NotNull
    @Column(name = "visibility", nullable = false)
    private Integer visibility;

    @Column(name = "min")
    private Integer min;

    @Column(name = "max")
    private Integer max;

    @Column(name = "step")
    private Integer step;

    @Column(name = "anonymous_voters")
    private Integer anonymousVoters;

    @NotNull
    @Column(name = "chart_type", nullable = false)
    private Integer chartType;

    @Column(name = "groups")
    private String groups;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "polls")
    private Set<Posts> posts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "polls", allowSetters = true)
    private PollVotes pollVotes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public Polls postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public Polls name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCloseAt() {
        return closeAt;
    }

    public Polls closeAt(Instant closeAt) {
        this.closeAt = closeAt;
        return this;
    }

    public void setCloseAt(Instant closeAt) {
        this.closeAt = closeAt;
    }

    public Integer getType() {
        return type;
    }

    public Polls type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public Polls status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResults() {
        return results;
    }

    public Polls results(Integer results) {
        this.results = results;
        return this;
    }

    public void setResults(Integer results) {
        this.results = results;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Polls visibility(Integer visibility) {
        this.visibility = visibility;
        return this;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getMin() {
        return min;
    }

    public Polls min(Integer min) {
        this.min = min;
        return this;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public Polls max(Integer max) {
        this.max = max;
        return this;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getStep() {
        return step;
    }

    public Polls step(Integer step) {
        this.step = step;
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getAnonymousVoters() {
        return anonymousVoters;
    }

    public Polls anonymousVoters(Integer anonymousVoters) {
        this.anonymousVoters = anonymousVoters;
        return this;
    }

    public void setAnonymousVoters(Integer anonymousVoters) {
        this.anonymousVoters = anonymousVoters;
    }

    public Integer getChartType() {
        return chartType;
    }

    public Polls chartType(Integer chartType) {
        this.chartType = chartType;
        return this;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public String getGroups() {
        return groups;
    }

    public Polls groups(String groups) {
        this.groups = groups;
        return this;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getTitle() {
        return title;
    }

    public Polls title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Posts> getPosts() {
        return posts;
    }

    public Polls posts(Set<Posts> posts) {
        this.posts = posts;
        return this;
    }

    public Polls addPosts(Posts posts) {
        this.posts.add(posts);
        posts.setPolls(this);
        return this;
    }

    public Polls removePosts(Posts posts) {
        this.posts.remove(posts);
        posts.setPolls(null);
        return this;
    }

    public void setPosts(Set<Posts> posts) {
        this.posts = posts;
    }

    public PollVotes getPollVotes() {
        return pollVotes;
    }

    public Polls pollVotes(PollVotes pollVotes) {
        this.pollVotes = pollVotes;
        return this;
    }

    public void setPollVotes(PollVotes pollVotes) {
        this.pollVotes = pollVotes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Polls)) {
            return false;
        }
        return id != null && id.equals(((Polls) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Polls{" +
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
            "}";
    }
}
