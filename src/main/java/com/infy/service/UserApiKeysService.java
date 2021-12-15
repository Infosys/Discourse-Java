/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserApiKeysDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserApiKeys}.
 */
public interface UserApiKeysService {

    /**
     * Save a userApiKeys.
     *
     * @param userApiKeysDTO the entity to save.
     * @return the persisted entity.
     */
    UserApiKeysDTO save(UserApiKeysDTO userApiKeysDTO);

    /**
     * Get all the userApiKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserApiKeysDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userApiKeys.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserApiKeysDTO> findOne(Long id);

    /**
     * Delete the "id" userApiKeys.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
