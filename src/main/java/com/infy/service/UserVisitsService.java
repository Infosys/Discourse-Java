/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserVisitsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserVisits}.
 */
public interface UserVisitsService {

    /**
     * Save a userVisits.
     *
     * @param userVisitsDTO the entity to save.
     * @return the persisted entity.
     */
    UserVisitsDTO save(UserVisitsDTO userVisitsDTO);

    /**
     * Get all the userVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserVisitsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userVisits.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserVisitsDTO> findOne(Long id);

    /**
     * Delete the "id" userVisits.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
