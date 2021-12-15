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
 * A DTO for the {@link com.infy.domain.PublishedPages} entity.
 */
public class PublishedPagesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long topicId;

    @NotNull
    private String slug;

    @NotNull
    private Boolean publiclyAvailable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean isPubliclyAvailable() {
        return publiclyAvailable;
    }

    public void setPubliclyAvailable(Boolean publiclyAvailable) {
        this.publiclyAvailable = publiclyAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublishedPagesDTO)) {
            return false;
        }

        return id != null && id.equals(((PublishedPagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublishedPagesDTO{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", slug='" + getSlug() + "'" +
            ", publiclyAvailable='" + isPubliclyAvailable() + "'" +
            "}";
    }
}
