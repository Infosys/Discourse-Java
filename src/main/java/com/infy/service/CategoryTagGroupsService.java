/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryTagGroupsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryTagGroups}.
 */
public interface CategoryTagGroupsService {

    /**
     * Save a categoryTagGroups.
     *
     * @param categoryTagGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryTagGroupsDTO save(CategoryTagGroupsDTO categoryTagGroupsDTO);

    /**
     * Get all the categoryTagGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryTagGroupsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryTagGroups.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryTagGroupsDTO> findOne(Long id);

    /**
     * Delete the "id" categoryTagGroups.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
