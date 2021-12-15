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
import java.time.Instant;

/**
 * A RemoteThemes.
 */
@Entity
@Table(name = "remote_themes")
public class RemoteThemes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "remote_url", nullable = false)
    private String remoteUrl;

    @Column(name = "remote_version")
    private String remoteVersion;

    @Column(name = "local_version")
    private String localVersion;

    @Column(name = "about_url")
    private String aboutUrl;

    @Column(name = "license_url")
    private String licenseUrl;

    @Column(name = "commits_behind")
    private Integer commitsBehind;

    @Column(name = "remote_updated_at")
    private Instant remoteUpdatedAt;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "branch")
    private String branch;

    @Column(name = "last_error_text")
    private String lastErrorText;

    @Column(name = "authors")
    private String authors;

    @Column(name = "theme_version")
    private String themeVersion;

    @Column(name = "minimum_discourse_version")
    private String minimumDiscourseVersion;

    @Column(name = "maximum_discourse_version")
    private String maximumDiscourseVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public RemoteThemes remoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
        return this;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getRemoteVersion() {
        return remoteVersion;
    }

    public RemoteThemes remoteVersion(String remoteVersion) {
        this.remoteVersion = remoteVersion;
        return this;
    }

    public void setRemoteVersion(String remoteVersion) {
        this.remoteVersion = remoteVersion;
    }

    public String getLocalVersion() {
        return localVersion;
    }

    public RemoteThemes localVersion(String localVersion) {
        this.localVersion = localVersion;
        return this;
    }

    public void setLocalVersion(String localVersion) {
        this.localVersion = localVersion;
    }

    public String getAboutUrl() {
        return aboutUrl;
    }

    public RemoteThemes aboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
        return this;
    }

    public void setAboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public RemoteThemes licenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
        return this;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public Integer getCommitsBehind() {
        return commitsBehind;
    }

    public RemoteThemes commitsBehind(Integer commitsBehind) {
        this.commitsBehind = commitsBehind;
        return this;
    }

    public void setCommitsBehind(Integer commitsBehind) {
        this.commitsBehind = commitsBehind;
    }

    public Instant getRemoteUpdatedAt() {
        return remoteUpdatedAt;
    }

    public RemoteThemes remoteUpdatedAt(Instant remoteUpdatedAt) {
        this.remoteUpdatedAt = remoteUpdatedAt;
        return this;
    }

    public void setRemoteUpdatedAt(Instant remoteUpdatedAt) {
        this.remoteUpdatedAt = remoteUpdatedAt;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public RemoteThemes privateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getBranch() {
        return branch;
    }

    public RemoteThemes branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLastErrorText() {
        return lastErrorText;
    }

    public RemoteThemes lastErrorText(String lastErrorText) {
        this.lastErrorText = lastErrorText;
        return this;
    }

    public void setLastErrorText(String lastErrorText) {
        this.lastErrorText = lastErrorText;
    }

    public String getAuthors() {
        return authors;
    }

    public RemoteThemes authors(String authors) {
        this.authors = authors;
        return this;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getThemeVersion() {
        return themeVersion;
    }

    public RemoteThemes themeVersion(String themeVersion) {
        this.themeVersion = themeVersion;
        return this;
    }

    public void setThemeVersion(String themeVersion) {
        this.themeVersion = themeVersion;
    }

    public String getMinimumDiscourseVersion() {
        return minimumDiscourseVersion;
    }

    public RemoteThemes minimumDiscourseVersion(String minimumDiscourseVersion) {
        this.minimumDiscourseVersion = minimumDiscourseVersion;
        return this;
    }

    public void setMinimumDiscourseVersion(String minimumDiscourseVersion) {
        this.minimumDiscourseVersion = minimumDiscourseVersion;
    }

    public String getMaximumDiscourseVersion() {
        return maximumDiscourseVersion;
    }

    public RemoteThemes maximumDiscourseVersion(String maximumDiscourseVersion) {
        this.maximumDiscourseVersion = maximumDiscourseVersion;
        return this;
    }

    public void setMaximumDiscourseVersion(String maximumDiscourseVersion) {
        this.maximumDiscourseVersion = maximumDiscourseVersion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemoteThemes)) {
            return false;
        }
        return id != null && id.equals(((RemoteThemes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemoteThemes{" +
            "id=" + getId() +
            ", remoteUrl='" + getRemoteUrl() + "'" +
            ", remoteVersion='" + getRemoteVersion() + "'" +
            ", localVersion='" + getLocalVersion() + "'" +
            ", aboutUrl='" + getAboutUrl() + "'" +
            ", licenseUrl='" + getLicenseUrl() + "'" +
            ", commitsBehind=" + getCommitsBehind() +
            ", remoteUpdatedAt='" + getRemoteUpdatedAt() + "'" +
            ", privateKey='" + getPrivateKey() + "'" +
            ", branch='" + getBranch() + "'" +
            ", lastErrorText='" + getLastErrorText() + "'" +
            ", authors='" + getAuthors() + "'" +
            ", themeVersion='" + getThemeVersion() + "'" +
            ", minimumDiscourseVersion='" + getMinimumDiscourseVersion() + "'" +
            ", maximumDiscourseVersion='" + getMaximumDiscourseVersion() + "'" +
            "}";
    }
}
