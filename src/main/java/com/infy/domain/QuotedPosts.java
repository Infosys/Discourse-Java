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

/**
 * A QuotedPosts.
 */
@Entity
@Table(name = "quoted_posts")
public class QuotedPosts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @NotNull
    @Column(name = "quoted_post_id", nullable = false)
    private Long quotedPostId;

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

    public QuotedPosts postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getQuotedPostId() {
        return quotedPostId;
    }

    public QuotedPosts quotedPostId(Long quotedPostId) {
        this.quotedPostId = quotedPostId;
        return this;
    }

    public void setQuotedPostId(Long quotedPostId) {
        this.quotedPostId = quotedPostId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuotedPosts)) {
            return false;
        }
        return id != null && id.equals(((QuotedPosts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuotedPosts{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", quotedPostId=" + getQuotedPostId() +
            "}";
    }
}
