/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupRequests}.
 */
public interface GroupRequestsService {

    /**
     * Save a groupRequests.
     *
     * @param groupRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    GroupRequestsDTO save(GroupRequestsDTO groupRequestsDTO);

    /**
     * Get all the groupRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" groupRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
