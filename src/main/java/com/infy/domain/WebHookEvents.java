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
 * A WebHookEvents.
 */
@Entity
@Table(name = "web_hook_events")
public class WebHookEvents extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "web_hook_id", nullable = false)
    private Long webHookId;

    @Column(name = "headers")
    private String headers;

    @Column(name = "payload")
    private String payload;

    @Column(name = "status")
    private Integer status;

    @Column(name = "response_headers")
    private String responseHeaders;

    @Column(name = "response_body")
    private String responseBody;

    @Column(name = "duration")
    private Integer duration;

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

    public WebHookEvents webHookId(Long webHookId) {
        this.webHookId = webHookId;
        return this;
    }

    public void setWebHookId(Long webHookId) {
        this.webHookId = webHookId;
    }

    public String getHeaders() {
        return headers;
    }

    public WebHookEvents headers(String headers) {
        this.headers = headers;
        return this;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getPayload() {
        return payload;
    }

    public WebHookEvents payload(String payload) {
        this.payload = payload;
        return this;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getStatus() {
        return status;
    }

    public WebHookEvents status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public WebHookEvents responseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
        return this;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public WebHookEvents responseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getDuration() {
        return duration;
    }

    public WebHookEvents duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebHookEvents)) {
            return false;
        }
        return id != null && id.equals(((WebHookEvents) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebHookEvents{" +
            "id=" + getId() +
            ", webHookId=" + getWebHookId() +
            ", headers='" + getHeaders() + "'" +
            ", payload='" + getPayload() + "'" +
            ", status=" + getStatus() +
            ", responseHeaders='" + getResponseHeaders() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            ", duration=" + getDuration() +
            "}";
    }
}
