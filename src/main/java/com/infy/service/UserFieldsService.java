/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserFields}.
 */
public interface UserFieldsService {

    /**
     * Save a userFields.
     *
     * @param userFieldsDTO the entity to save.
     * @return the persisted entity.
     */
    UserFieldsDTO save(UserFieldsDTO userFieldsDTO);

    /**
     * Get all the userFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" userFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
