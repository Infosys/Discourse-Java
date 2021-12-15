/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.RemoteThemes} entity.
 */
public class RemoteThemesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String remoteUrl;

    private String remoteVersion;

    private String localVersion;

    private String aboutUrl;

    private String licenseUrl;

    private Integer commitsBehind;

    private Instant remoteUpdatedAt;

    private String privateKey;

    private String branch;

    private String lastErrorText;

    private String authors;

    private String themeVersion;

    private String minimumDiscourseVersion;

    private String maximumDiscourseVersion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getRemoteVersion() {
        return remoteVersion;
    }

    public void setRemoteVersion(String remoteVersion) {
        this.remoteVersion = remoteVersion;
    }

    public String getLocalVersion() {
        return localVersion;
    }

    public void setLocalVersion(String localVersion) {
        this.localVersion = localVersion;
    }

    public String getAboutUrl() {
        return aboutUrl;
    }

    public void setAboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public Integer getCommitsBehind() {
        return commitsBehind;
    }

    public void setCommitsBehind(Integer commitsBehind) {
        this.commitsBehind = commitsBehind;
    }

    public Instant getRemoteUpdatedAt() {
        return remoteUpdatedAt;
    }

    public void setRemoteUpdatedAt(Instant remoteUpdatedAt) {
        this.remoteUpdatedAt = remoteUpdatedAt;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLastErrorText() {
        return lastErrorText;
    }

    public void setLastErrorText(String lastErrorText) {
        this.lastErrorText = lastErrorText;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getThemeVersion() {
        return themeVersion;
    }

    public void setThemeVersion(String themeVersion) {
        this.themeVersion = themeVersion;
    }

    public String getMinimumDiscourseVersion() {
        return minimumDiscourseVersion;
    }

    public void setMinimumDiscourseVersion(String minimumDiscourseVersion) {
        this.minimumDiscourseVersion = minimumDiscourseVersion;
    }

    public String getMaximumDiscourseVersion() {
        return maximumDiscourseVersion;
    }

    public void setMaximumDiscourseVersion(String maximumDiscourseVersion) {
        this.maximumDiscourseVersion = maximumDiscourseVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemoteThemesDTO)) {
            return false;
        }

        return id != null && id.equals(((RemoteThemesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemoteThemesDTO{" +
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
