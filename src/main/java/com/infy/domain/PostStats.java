/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A PostStats.
 */
@Entity
@Table(name = "post_stats")
public class PostStats extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "drafts_saved")
    private Integer draftsSaved;

    @Column(name = "typing_duration_msecs")
    private Integer typingDurationMsecs;

    @Column(name = "composer_open_duration_msecs")
    private Integer composerOpenDurationMsecs;

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

    public PostStats postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Integer getDraftsSaved() {
        return draftsSaved;
    }

    public PostStats draftsSaved(Integer draftsSaved) {
        this.draftsSaved = draftsSaved;
        return this;
    }

    public void setDraftsSaved(Integer draftsSaved) {
        this.draftsSaved = draftsSaved;
    }

    public Integer getTypingDurationMsecs() {
        return typingDurationMsecs;
    }

    public PostStats typingDurationMsecs(Integer typingDurationMsecs) {
        this.typingDurationMsecs = typingDurationMsecs;
        return this;
    }

    public void setTypingDurationMsecs(Integer typingDurationMsecs) {
        this.typingDurationMsecs = typingDurationMsecs;
    }

    public Integer getComposerOpenDurationMsecs() {
        return composerOpenDurationMsecs;
    }

    public PostStats composerOpenDurationMsecs(Integer composerOpenDurationMsecs) {
        this.composerOpenDurationMsecs = composerOpenDurationMsecs;
        return this;
    }

    public void setComposerOpenDurationMsecs(Integer composerOpenDurationMsecs) {
        this.composerOpenDurationMsecs = composerOpenDurationMsecs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostStats)) {
            return false;
        }
        return id != null && id.equals(((PostStats) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostStats{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", draftsSaved=" + getDraftsSaved() +
            ", typingDurationMsecs=" + getTypingDurationMsecs() +
            ", composerOpenDurationMsecs=" + getComposerOpenDurationMsecs() +
            "}";
    }
}
