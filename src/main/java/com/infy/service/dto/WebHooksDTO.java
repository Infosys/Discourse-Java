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
 * A DTO for the {@link com.infy.domain.WebHooks} entity.
 */
public class WebHooksDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String payloadUrl;

    @NotNull
    private Integer contentType;

    @NotNull
    private Integer lastDeliveryStatus;

    @NotNull
    private Integer status;

    private String secret;

    @NotNull
    private Boolean wildcardWebHook;

    @NotNull
    private Boolean verifyCertificate;

    @NotNull
    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayloadUrl() {
        return payloadUrl;
    }

    public void setPayloadUrl(String payloadUrl) {
        this.payloadUrl = payloadUrl;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getLastDeliveryStatus() {
        return lastDeliveryStatus;
    }

    public void setLastDeliveryStatus(Integer lastDeliveryStatus) {
        this.lastDeliveryStatus = lastDeliveryStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean isWildcardWebHook() {
        return wildcardWebHook;
    }

    public void setWildcardWebHook(Boolean wildcardWebHook) {
        this.wildcardWebHook = wildcardWebHook;
    }

    public Boolean isVerifyCertificate() {
        return verifyCertificate;
    }

    public void setVerifyCertificate(Boolean verifyCertificate) {
        this.verifyCertificate = verifyCertificate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebHooksDTO)) {
            return false;
        }

        return id != null && id.equals(((WebHooksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebHooksDTO{" +
            "id=" + getId() +
            ", payloadUrl='" + getPayloadUrl() + "'" +
            ", contentType=" + getContentType() +
            ", lastDeliveryStatus=" + getLastDeliveryStatus() +
            ", status=" + getStatus() +
            ", secret='" + getSecret() + "'" +
            ", wildcardWebHook='" + isWildcardWebHook() + "'" +
            ", verifyCertificate='" + isVerifyCertificate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
