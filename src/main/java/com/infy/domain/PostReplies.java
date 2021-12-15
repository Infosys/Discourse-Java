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
 * A PostReplies.
 */
@Entity
@Table(name = "post_replies")
public class PostReplies extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "reply_post_id")
    private Long replyPostId;

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

    public PostReplies postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getReplyPostId() {
        return replyPostId;
    }

    public PostReplies replyPostId(Long replyPostId) {
        this.replyPostId = replyPostId;
        return this;
    }

    public void setReplyPostId(Long replyPostId) {
        this.replyPostId = replyPostId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostReplies)) {
            return false;
        }
        return id != null && id.equals(((PostReplies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostReplies{" +
            "id=" + getId() +
            ", postId=" + getPostId() +
            ", replyPostId=" + getReplyPostId() +
            "}";
    }
}
