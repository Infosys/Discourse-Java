/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserCustomFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserCustomFields}.
 */
public interface UserCustomFieldsService {

    /**
     * Save a userCustomFields.
     *
     * @param userCustomFieldsDTO the entity to save.
     * @return the persisted entity.
     */
    UserCustomFieldsDTO save(UserCustomFieldsDTO userCustomFieldsDTO);

    /**
     * Get all the userCustomFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserCustomFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userCustomFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserCustomFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" userCustomFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
