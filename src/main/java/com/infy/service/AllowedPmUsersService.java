/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.AllowedPmUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.AllowedPmUsers}.
 */
public interface AllowedPmUsersService {

    /**
     * Save a allowedPmUsers.
     *
     * @param allowedPmUsersDTO the entity to save.
     * @return the persisted entity.
     */
    AllowedPmUsersDTO save(AllowedPmUsersDTO allowedPmUsersDTO);

    /**
     * Get all the allowedPmUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllowedPmUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" allowedPmUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AllowedPmUsersDTO> findOne(Long id);

    /**
     * Delete the "id" allowedPmUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
