/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryFeaturedTopicsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryFeaturedTopics}.
 */
public interface CategoryFeaturedTopicsService {

    /**
     * Save a categoryFeaturedTopics.
     *
     * @param categoryFeaturedTopicsDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryFeaturedTopicsDTO save(CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO);

    /**
     * Get all the categoryFeaturedTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryFeaturedTopicsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryFeaturedTopics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryFeaturedTopicsDTO> findOne(Long id);

    /**
     * Delete the "id" categoryFeaturedTopics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
