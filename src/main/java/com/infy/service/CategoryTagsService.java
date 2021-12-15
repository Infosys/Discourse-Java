/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryTagsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryTags}.
 */
public interface CategoryTagsService {

    /**
     * Save a categoryTags.
     *
     * @param categoryTagsDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryTagsDTO save(CategoryTagsDTO categoryTagsDTO);

    /**
     * Get all the categoryTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryTagsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryTags.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryTagsDTO> findOne(Long id);

    /**
     * Delete the "id" categoryTags.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
