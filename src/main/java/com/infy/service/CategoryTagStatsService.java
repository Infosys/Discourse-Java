/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryTagStatsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryTagStats}.
 */
public interface CategoryTagStatsService {

    /**
     * Save a categoryTagStats.
     *
     * @param categoryTagStatsDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryTagStatsDTO save(CategoryTagStatsDTO categoryTagStatsDTO);

    /**
     * Get all the categoryTagStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryTagStatsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryTagStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryTagStatsDTO> findOne(Long id);

    /**
     * Delete the "id" categoryTagStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
