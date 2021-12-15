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
 * A UnsubscribeKeys.
 */
@Entity
@Table(name = "unsubscribe_keys")
public class UnsubscribeKeys extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "unsubscribe_key_type")
    private String unsubscribeKeyType;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "post_id")
    private Long postId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public UnsubscribeKeys key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public UnsubscribeKeys userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnsubscribeKeyType() {
        return unsubscribeKeyType;
    }

    public UnsubscribeKeys unsubscribeKeyType(String unsubscribeKeyType) {
        this.unsubscribeKeyType = unsubscribeKeyType;
        return this;
    }

    public void setUnsubscribeKeyType(String unsubscribeKeyType) {
        this.unsubscribeKeyType = unsubscribeKeyType;
    }

    public Long getTopicId() {
        return topicId;
    }

    public UnsubscribeKeys topicId(Long topicId) {
        this.topicId = topicId;
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getPostId() {
        return postId;
    }

    public UnsubscribeKeys postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnsubscribeKeys)) {
            return false;
        }
        return id != null && id.equals(((UnsubscribeKeys) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnsubscribeKeys{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", userId='" + getUserId() + "'" +
            ", unsubscribeKeyType='" + getUnsubscribeKeyType() + "'" +
            ", topicId=" + getTopicId() +
            ", postId=" + getPostId() +
            "}";
    }
}
