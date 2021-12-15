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
 * A CategoriesWebHooks.
 */
@Entity
@Table(name = "categories_web_hooks")
public class CategoriesWebHooks extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "web_hook_id", nullable = false)
    private Long webHookId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

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

    public CategoriesWebHooks webHookId(Long webHookId) {
        this.webHookId = webHookId;
        return this;
    }

    public void setWebHookId(Long webHookId) {
        this.webHookId = webHookId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public CategoriesWebHooks categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriesWebHooks)) {
            return false;
        }
        return id != null && id.equals(((CategoriesWebHooks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesWebHooks{" +
            "id=" + getId() +
            ", webHookId=" + getWebHookId() +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
