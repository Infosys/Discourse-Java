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
 * A CustomEmojis.
 */
@Entity
@Table(name = "custom_emojis")
public class CustomEmojis extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "upload_id", nullable = false)
    private Long uploadId;

    @Column(name = "jhi_group")
    private String group;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CustomEmojis name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public CustomEmojis uploadId(Long uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getGroup() {
        return group;
    }

    public CustomEmojis group(String group) {
        this.group = group;
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomEmojis)) {
            return false;
        }
        return id != null && id.equals(((CustomEmojis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomEmojis{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uploadId=" + getUploadId() +
            ", group='" + getGroup() + "'" +
            "}";
    }
}
