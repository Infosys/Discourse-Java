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
 * A IncomingLinks.
 */
@Entity
@Table(name = "incoming_links")
public class IncomingLinks extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "current_user_id")
    private String currentUserId;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "incoming_referer_id")
    private Long incomingRefererId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public IncomingLinks userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public IncomingLinks ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public IncomingLinks currentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
        return this;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Long getPostId() {
        return postId;
    }

    public IncomingLinks postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getIncomingRefererId() {
        return incomingRefererId;
    }

    public IncomingLinks incomingRefererId(Long incomingRefererId) {
        this.incomingRefererId = incomingRefererId;
        return this;
    }

    public void setIncomingRefererId(Long incomingRefererId) {
        this.incomingRefererId = incomingRefererId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingLinks)) {
            return false;
        }
        return id != null && id.equals(((IncomingLinks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingLinks{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", currentUserId='" + getCurrentUserId() + "'" +
            ", postId=" + getPostId() +
            ", incomingRefererId=" + getIncomingRefererId() +
            "}";
    }
}
