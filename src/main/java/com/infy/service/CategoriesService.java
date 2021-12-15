/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.infy.service.dto.CategoriesDTO;
import com.infy.service.model.CategoryList;
import com.infy.service.model.CategoryResponse;
import com.infy.service.model.CreateCategoryRequest;
import com.infy.service.model.UpdateCategoryResponse;

/**
 * Service Interface for managing {@link com.infy.domain.Categories}.
 */
public interface CategoriesService {

    /**
     * Save a categories.
     *
     * @param categoriesDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriesDTO save(CategoriesDTO categoriesDTO);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" categories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    CategoryResponse saveAndResponse(CreateCategoryRequest createCategoryRequest);

    CategoryList getAllCategories(Pageable pageable);

    UpdateCategoryResponse updateCategory(Long id,CreateCategoryRequest createCategoryRequest);
}
