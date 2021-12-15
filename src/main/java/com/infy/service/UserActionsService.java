/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserActionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserActions}.
 */
public interface UserActionsService {

    /**
     * Save a userActions.
     *
     * @param userActionsDTO the entity to save.
     * @return the persisted entity.
     */
    UserActionsDTO save(UserActionsDTO userActionsDTO);

    /**
     * Get all the userActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserActionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userActions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserActionsDTO> findOne(Long id);

    /**
     * Delete the "id" userActions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
