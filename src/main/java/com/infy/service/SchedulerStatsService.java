/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SchedulerStatsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SchedulerStats}.
 */
public interface SchedulerStatsService {

    /**
     * Save a schedulerStats.
     *
     * @param schedulerStatsDTO the entity to save.
     * @return the persisted entity.
     */
    SchedulerStatsDTO save(SchedulerStatsDTO schedulerStatsDTO);

    /**
     * Get all the schedulerStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchedulerStatsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" schedulerStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchedulerStatsDTO> findOne(Long id);

    /**
     * Delete the "id" schedulerStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
