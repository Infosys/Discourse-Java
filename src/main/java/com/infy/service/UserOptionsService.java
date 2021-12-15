/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserOptionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserOptions}.
 */
public interface UserOptionsService {

    /**
     * Save a userOptions.
     *
     * @param userOptionsDTO the entity to save.
     * @return the persisted entity.
     */
    UserOptionsDTO save(UserOptionsDTO userOptionsDTO);

    /**
     * Get all the userOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserOptionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userOptions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserOptionsDTO> findOne(Long id);

    /**
     * Delete the "id" userOptions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
