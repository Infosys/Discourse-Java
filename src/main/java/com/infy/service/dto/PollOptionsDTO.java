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
 * A DTO for the {@link com.infy.domain.PollOptions} entity.
 */
public class PollOptionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long pollId;

    @NotNull
    private String digest;

    @NotNull
    private String html;

    private Integer anonymousVotes;


    private Long pollVotesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getAnonymousVotes() {
        return anonymousVotes;
    }

    public void setAnonymousVotes(Integer anonymousVotes) {
        this.anonymousVotes = anonymousVotes;
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
        if (!(o instanceof PollOptionsDTO)) {
            return false;
        }

        return id != null && id.equals(((PollOptionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollOptionsDTO{" +
            "id=" + getId() +
            ", pollId=" + getPollId() +
            ", digest='" + getDigest() + "'" +
            ", html='" + getHtml() + "'" +
            ", anonymousVotes=" + getAnonymousVotes() +
            ", pollVotesId=" + getPollVotesId() +
            "}";
    }
}
