/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryCustomFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryCustomFields}.
 */
public interface CategoryCustomFieldsService {

    /**
     * Save a categoryCustomFields.
     *
     * @param categoryCustomFieldsDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryCustomFieldsDTO save(CategoryCustomFieldsDTO categoryCustomFieldsDTO);

    /**
     * Get all the categoryCustomFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryCustomFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryCustomFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryCustomFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" categoryCustomFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
