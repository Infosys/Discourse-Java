/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.MutedUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.MutedUsers}.
 */
public interface MutedUsersService {

    /**
     * Save a mutedUsers.
     *
     * @param mutedUsersDTO the entity to save.
     * @return the persisted entity.
     */
    MutedUsersDTO save(MutedUsersDTO mutedUsersDTO);

    /**
     * Get all the mutedUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MutedUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mutedUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MutedUsersDTO> findOne(Long id);

    /**
     * Delete the "id" mutedUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
