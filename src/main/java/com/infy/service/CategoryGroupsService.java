/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryGroupsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryGroups}.
 */
public interface CategoryGroupsService {

    /**
     * Save a categoryGroups.
     *
     * @param categoryGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryGroupsDTO save(CategoryGroupsDTO categoryGroupsDTO);

    /**
     * Get all the categoryGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryGroupsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryGroups.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryGroupsDTO> findOne(Long id);

    /**
     * Delete the "id" categoryGroups.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
