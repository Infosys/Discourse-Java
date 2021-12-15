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
 * A DTO for the {@link com.infy.domain.WebHookEvents} entity.
 */
public class WebHookEventsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long webHookId;

    private String headers;

    private String payload;

    private Integer status;

    private String responseHeaders;

    private String responseBody;

    private Integer duration;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWebHookId() {
        return webHookId;
    }

    public void setWebHookId(Long webHookId) {
        this.webHookId = webHookId;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebHookEventsDTO)) {
            return false;
        }

        return id != null && id.equals(((WebHookEventsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebHookEventsDTO{" +
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
