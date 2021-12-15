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
 * A ShelvedNotifications.
 */
@Entity
@Table(name = "shelved_notifications")
public class ShelvedNotifications extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public ShelvedNotifications notificationId(Long notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShelvedNotifications)) {
            return false;
        }
        return id != null && id.equals(((ShelvedNotifications) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShelvedNotifications{" +
            "id=" + getId() +
            ", notificationId=" + getNotificationId() +
            "}";
    }
}
