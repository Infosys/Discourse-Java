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
 * A DTO for the {@link com.infy.domain.SchemaMigrationDetails} entity.
 */
public class SchemaMigrationDetailsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String version;

    private String name;

    private String hostname;

    private String gitVersion;

    private String railsVersion;

    private Integer duration;

    private String direction;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getGitVersion() {
        return gitVersion;
    }

    public void setGitVersion(String gitVersion) {
        this.gitVersion = gitVersion;
    }

    public String getRailsVersion() {
        return railsVersion;
    }

    public void setRailsVersion(String railsVersion) {
        this.railsVersion = railsVersion;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemaMigrationDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((SchemaMigrationDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchemaMigrationDetailsDTO{" +
            "id=" + getId() +
            ", version='" + getVersion() + "'" +
            ", name='" + getName() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", gitVersion='" + getGitVersion() + "'" +
            ", railsVersion='" + getRailsVersion() + "'" +
            ", duration=" + getDuration() +
            ", direction='" + getDirection() + "'" +
            "}";
    }
}
