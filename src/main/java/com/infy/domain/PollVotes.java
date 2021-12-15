/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PollVotes.
 */
@Entity
@Table(name = "poll_votes")
public class PollVotes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "poll_id")
    private Long pollId;

    @Column(name = "poll_option_id")
    private Long pollOptionId;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "pollVotes")
    private Set<PollOptions> pollOptions = new HashSet<>();

    @OneToMany(mappedBy = "pollVotes")
    private Set<Polls> polls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPollId() {
        return pollId;
    }

    public PollVotes pollId(Long pollId) {
        this.pollId = pollId;
        return this;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getPollOptionId() {
        return pollOptionId;
    }

    public PollVotes pollOptionId(Long pollOptionId) {
        this.pollOptionId = pollOptionId;
        return this;
    }

    public void setPollOptionId(Long pollOptionId) {
        this.pollOptionId = pollOptionId;
    }

    public Long getUserId() {
        return userId;
    }

    public PollVotes userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<PollOptions> getPollOptions() {
        return pollOptions;
    }

    public PollVotes pollOptions(Set<PollOptions> pollOptions) {
        this.pollOptions = pollOptions;
        return this;
    }

    public PollVotes addPollOptions(PollOptions pollOptions) {
        this.pollOptions.add(pollOptions);
        pollOptions.setPollVotes(this);
        return this;
    }

    public PollVotes removePollOptions(PollOptions pollOptions) {
        this.pollOptions.remove(pollOptions);
        pollOptions.setPollVotes(null);
        return this;
    }

    public void setPollOptions(Set<PollOptions> pollOptions) {
        this.pollOptions = pollOptions;
    }

    public Set<Polls> getPolls() {
        return polls;
    }

    public PollVotes polls(Set<Polls> polls) {
        this.polls = polls;
        return this;
    }

    public PollVotes addPolls(Polls polls) {
        this.polls.add(polls);
        polls.setPollVotes(this);
        return this;
    }

    public PollVotes removePolls(Polls polls) {
        this.polls.remove(polls);
        polls.setPollVotes(null);
        return this;
    }

    public void setPolls(Set<Polls> polls) {
        this.polls = polls;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollVotes)) {
            return false;
        }
        return id != null && id.equals(((PollVotes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollVotes{" +
            "id=" + getId() +
            ", pollId=" + getPollId() +
            ", pollOptionId=" + getPollOptionId() +
            ", userId=" + getUserId() +
            "}";
    }
}
