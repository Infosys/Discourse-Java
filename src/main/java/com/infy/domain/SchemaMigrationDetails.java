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
 * A SchemaMigrationDetails.
 */
@Entity
@Table(name = "schema_migration_details")
public class SchemaMigrationDetails extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "name")
    private String name;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "git_version")
    private String gitVersion;

    @Column(name = "rails_version")
    private String railsVersion;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "direction")
    private String direction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public SchemaMigrationDetails version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public SchemaMigrationDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public SchemaMigrationDetails hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getGitVersion() {
        return gitVersion;
    }

    public SchemaMigrationDetails gitVersion(String gitVersion) {
        this.gitVersion = gitVersion;
        return this;
    }

    public void setGitVersion(String gitVersion) {
        this.gitVersion = gitVersion;
    }

    public String getRailsVersion() {
        return railsVersion;
    }

    public SchemaMigrationDetails railsVersion(String railsVersion) {
        this.railsVersion = railsVersion;
        return this;
    }

    public void setRailsVersion(String railsVersion) {
        this.railsVersion = railsVersion;
    }

    public Integer getDuration() {
        return duration;
    }

    public SchemaMigrationDetails duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDirection() {
        return direction;
    }

    public SchemaMigrationDetails direction(String direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemaMigrationDetails)) {
            return false;
        }
        return id != null && id.equals(((SchemaMigrationDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchemaMigrationDetails{" +
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
