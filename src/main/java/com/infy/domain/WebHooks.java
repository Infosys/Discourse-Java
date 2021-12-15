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
 * A WebHooks.
 */
@Entity
@Table(name = "web_hooks")
public class WebHooks extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "payload_url", nullable = false)
    private String payloadUrl;

    @NotNull
    @Column(name = "content_type", nullable = false)
    private Integer contentType;

    @NotNull
    @Column(name = "last_delivery_status", nullable = false)
    private Integer lastDeliveryStatus;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "secret")
    private String secret;

    @NotNull
    @Column(name = "wildcard_web_hook", nullable = false)
    private Boolean wildcardWebHook;

    @NotNull
    @Column(name = "verify_certificate", nullable = false)
    private Boolean verifyCertificate;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayloadUrl() {
        return payloadUrl;
    }

    public WebHooks payloadUrl(String payloadUrl) {
        this.payloadUrl = payloadUrl;
        return this;
    }

    public void setPayloadUrl(String payloadUrl) {
        this.payloadUrl = payloadUrl;
    }

    public Integer getContentType() {
        return contentType;
    }

    public WebHooks contentType(Integer contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getLastDeliveryStatus() {
        return lastDeliveryStatus;
    }

    public WebHooks lastDeliveryStatus(Integer lastDeliveryStatus) {
        this.lastDeliveryStatus = lastDeliveryStatus;
        return this;
    }

    public void setLastDeliveryStatus(Integer lastDeliveryStatus) {
        this.lastDeliveryStatus = lastDeliveryStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public WebHooks status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSecret() {
        return secret;
    }

    public WebHooks secret(String secret) {
        this.secret = secret;
        return this;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean isWildcardWebHook() {
        return wildcardWebHook;
    }

    public WebHooks wildcardWebHook(Boolean wildcardWebHook) {
        this.wildcardWebHook = wildcardWebHook;
        return this;
    }

    public void setWildcardWebHook(Boolean wildcardWebHook) {
        this.wildcardWebHook = wildcardWebHook;
    }

    public Boolean isVerifyCertificate() {
        return verifyCertificate;
    }

    public WebHooks verifyCertificate(Boolean verifyCertificate) {
        this.verifyCertificate = verifyCertificate;
        return this;
    }

    public void setVerifyCertificate(Boolean verifyCertificate) {
        this.verifyCertificate = verifyCertificate;
    }

    public Boolean isActive() {
        return active;
    }

    public WebHooks active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebHooks)) {
            return false;
        }
        return id != null && id.equals(((WebHooks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WebHooks{" +
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
