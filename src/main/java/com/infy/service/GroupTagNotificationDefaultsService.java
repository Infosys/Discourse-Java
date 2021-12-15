/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupTagNotificationDefaultsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupTagNotificationDefaults}.
 */
public interface GroupTagNotificationDefaultsService {

    /**
     * Save a groupTagNotificationDefaults.
     *
     * @param groupTagNotificationDefaultsDTO the entity to save.
     * @return the persisted entity.
     */
    GroupTagNotificationDefaultsDTO save(GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO);

    /**
     * Get all the groupTagNotificationDefaults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupTagNotificationDefaultsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupTagNotificationDefaults.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupTagNotificationDefaultsDTO> findOne(Long id);

    /**
     * Delete the "id" groupTagNotificationDefaults.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
