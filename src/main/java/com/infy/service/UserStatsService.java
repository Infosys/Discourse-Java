/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserStatsDTO;
import com.infy.service.model.UserStatsResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserStats}.
 */
public interface UserStatsService {

    /**
     * Save a userStats.
     *
     * @param userStatsDTO the entity to save.
     * @return the persisted entity.
     */
    UserStatsDTO save(UserStatsDTO userStatsDTO);

    /**
     * Get all the userStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserStatsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserStatsDTO> findOne(Long id);

    /**
     * Delete the "id" userStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<UserStatsDTO> findByUserId(String userId);

    Optional<UserStatsResponse> findByCurrentLoginUser();
}
