/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserHistoriesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserHistories}.
 */
public interface UserHistoriesService {

    /**
     * Save a userHistories.
     *
     * @param userHistoriesDTO the entity to save.
     * @return the persisted entity.
     */
    UserHistoriesDTO save(UserHistoriesDTO userHistoriesDTO);

    /**
     * Get all the userHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserHistoriesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userHistories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserHistoriesDTO> findOne(Long id);

    /**
     * Delete the "id" userHistories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
