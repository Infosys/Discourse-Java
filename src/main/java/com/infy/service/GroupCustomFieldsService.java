/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupCustomFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupCustomFields}.
 */
public interface GroupCustomFieldsService {

    /**
     * Save a groupCustomFields.
     *
     * @param groupCustomFieldsDTO the entity to save.
     * @return the persisted entity.
     */
    GroupCustomFieldsDTO save(GroupCustomFieldsDTO groupCustomFieldsDTO);

    /**
     * Get all the groupCustomFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupCustomFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupCustomFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupCustomFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" groupCustomFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
