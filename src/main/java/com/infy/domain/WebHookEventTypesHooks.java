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
 * A WebHookEventTypesHooks.
 */
@Entity
@Table(name = "web_hook_event_types_hooks")
public class WebHookEventTypesHooks extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "web_hook_id", nullable = false)
    private Long webHookId;

    @NotNull
    @Column(name = "web_hook_event_type_id", nullable = false)
    private Long webHookEventTypeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebHookId() {
        return webHookId;
    }

    public WebHookEventTypesHooks webHookId(Long webHookId) {
        this.webHookId = webHookId;
        return this;
    }

    public void setWebHookId(Long webHookId) {
        this.webHookId = webHookId;
    }

    public Long getWebHookEventTypeId() {
        return webHookEventTypeId;
    }

    public WebHookEventTypesHooks webHookEventTypeId(Long webHookEventTypeId) {
        this.webHookEventTypeId = webHookEventTypeId;
        return this;
    }

    public void setWebHookEventTypeId(Long webHookEventTypeId) {
        this.webHookEventTypeId = webHookEventTypeId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebHookEventTypesHooks)) {
            return false;
        }
        return id != null && id.equals(((WebHookEventTypesHooks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebHookEventTypesHooks{" +
            "id=" + getId() +
            ", webHookId=" + getWebHookId() +
            ", webHookEventTypeId=" + getWebHookEventTypeId() +
            "}";
    }
}
