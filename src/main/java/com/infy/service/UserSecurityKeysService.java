/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserSecurityKeysDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserSecurityKeys}.
 */
public interface UserSecurityKeysService {

    /**
     * Save a userSecurityKeys.
     *
     * @param userSecurityKeysDTO the entity to save.
     * @return the persisted entity.
     */
    UserSecurityKeysDTO save(UserSecurityKeysDTO userSecurityKeysDTO);

    /**
     * Get all the userSecurityKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserSecurityKeysDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userSecurityKeys.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSecurityKeysDTO> findOne(Long id);

    /**
     * Delete the "id" userSecurityKeys.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
