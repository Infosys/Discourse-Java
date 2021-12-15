/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupCategoryNotificationDefaultsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupCategoryNotificationDefaults}.
 */
public interface GroupCategoryNotificationDefaultsService {

    /**
     * Save a groupCategoryNotificationDefaults.
     *
     * @param groupCategoryNotificationDefaultsDTO the entity to save.
     * @return the persisted entity.
     */
    GroupCategoryNotificationDefaultsDTO save(GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO);

    /**
     * Get all the groupCategoryNotificationDefaults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupCategoryNotificationDefaultsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupCategoryNotificationDefaults.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupCategoryNotificationDefaultsDTO> findOne(Long id);

    /**
     * Delete the "id" groupCategoryNotificationDefaults.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
