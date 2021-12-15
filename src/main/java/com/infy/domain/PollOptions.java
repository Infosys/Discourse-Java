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

/**
 * A PollOptions.
 */
@Entity
@Table(name = "poll_options")
public class PollOptions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "poll_id")
    private Long pollId;

    @NotNull
    @Column(name = "digest", nullable = false)
    private String digest;

    @NotNull
    @Column(name = "html", nullable = false)
    private String html;

    @Column(name = "anonymous_votes")
    private Integer anonymousVotes;

    @ManyToOne
    @JsonIgnoreProperties(value = "pollOptions", allowSetters = true)
    private PollVotes pollVotes;

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

    public PollOptions pollId(Long pollId) {
        this.pollId = pollId;
        return this;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getDigest() {
        return digest;
    }

    public PollOptions digest(String digest) {
        this.digest = digest;
        return this;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getHtml() {
        return html;
    }

    public PollOptions html(String html) {
        this.html = html;
        return this;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getAnonymousVotes() {
        return anonymousVotes;
    }

    public PollOptions anonymousVotes(Integer anonymousVotes) {
        this.anonymousVotes = anonymousVotes;
        return this;
    }

    public void setAnonymousVotes(Integer anonymousVotes) {
        this.anonymousVotes = anonymousVotes;
    }

    public PollVotes getPollVotes() {
        return pollVotes;
    }

    public PollOptions pollVotes(PollVotes pollVotes) {
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
        if (!(o instanceof PollOptions)) {
            return false;
        }
        return id != null && id.equals(((PollOptions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollOptions{" +
            "id=" + getId() +
            ", pollId=" + getPollId() +
            ", digest='" + getDigest() + "'" +
            ", html='" + getHtml() + "'" +
            ", anonymousVotes=" + getAnonymousVotes() +
            "}";
    }
}
