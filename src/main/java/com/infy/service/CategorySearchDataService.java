/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategorySearchDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategorySearchData}.
 */
public interface CategorySearchDataService {

    /**
     * Save a categorySearchData.
     *
     * @param categorySearchDataDTO the entity to save.
     * @return the persisted entity.
     */
    CategorySearchDataDTO save(CategorySearchDataDTO categorySearchDataDTO);

    /**
     * Get all the categorySearchData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorySearchDataDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categorySearchData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorySearchDataDTO> findOne(Long id);

    /**
     * Delete the "id" categorySearchData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
