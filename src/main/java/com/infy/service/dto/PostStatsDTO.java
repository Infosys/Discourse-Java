/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.PostStats} entity.
 */
public class PostStatsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long postId;

    private Integer draftsSaved;

    private Integer typingDurationMsecs;

    private Integer composerOpenDurationMsecs;


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

    public Integer getDraftsSaved() {
        return draftsSaved;
    }

    public void setDraftsSaved(Integer draftsSaved) {
        this.draftsSaved = draftsSaved;
    }

    public Integer getTypingDurationMsecs() {
        return typingDurationMsecs;
    }

    public void setTypingDurationMsecs(Integer typingDurationMsecs) {
        this.typingDurationMsecs = typingDurationMsecs;
    }

    public Integer getComposerOpenDurationMsecs() {
        return composerOpenDurationMsecs;
    }

    public void setComposerOpenDurationMsecs(Integer composerOpenDurationMsecs) {
        this.composerOpenDurationMsecs = composerOpenDurationMsecs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostStatsDTO)) {
            return false;
        }

        return id != null && id.equals(((PostStatsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostStatsDTO{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", draftsSaved=" + getDraftsSaved() +
            ", typingDurationMsecs=" + getTypingDurationMsecs() +
            ", composerOpenDurationMsecs=" + getComposerOpenDurationMsecs() +
            "}";
    }
}
