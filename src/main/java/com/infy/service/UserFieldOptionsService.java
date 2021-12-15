/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserFieldOptionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserFieldOptions}.
 */
public interface UserFieldOptionsService {

    /**
     * Save a userFieldOptions.
     *
     * @param userFieldOptionsDTO the entity to save.
     * @return the persisted entity.
     */
    UserFieldOptionsDTO save(UserFieldOptionsDTO userFieldOptionsDTO);

    /**
     * Get all the userFieldOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserFieldOptionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userFieldOptions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserFieldOptionsDTO> findOne(Long id);

    /**
     * Delete the "id" userFieldOptions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
