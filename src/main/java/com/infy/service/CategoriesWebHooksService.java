/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoriesWebHooksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoriesWebHooks}.
 */
public interface CategoriesWebHooksService {

    /**
     * Save a categoriesWebHooks.
     *
     * @param categoriesWebHooksDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriesWebHooksDTO save(CategoriesWebHooksDTO categoriesWebHooksDTO);

    /**
     * Get all the categoriesWebHooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriesWebHooksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoriesWebHooks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriesWebHooksDTO> findOne(Long id);

    /**
     * Delete the "id" categoriesWebHooks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
