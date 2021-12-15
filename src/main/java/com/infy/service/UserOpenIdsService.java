/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserOpenIdsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserOpenIds}.
 */
public interface UserOpenIdsService {

    /**
     * Save a userOpenIds.
     *
     * @param userOpenIdsDTO the entity to save.
     * @return the persisted entity.
     */
    UserOpenIdsDTO save(UserOpenIdsDTO userOpenIdsDTO);

    /**
     * Get all the userOpenIds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserOpenIdsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userOpenIds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserOpenIdsDTO> findOne(Long id);

    /**
     * Delete the "id" userOpenIds.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
