/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.DismissedTopicUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.DismissedTopicUsers}.
 */
public interface DismissedTopicUsersService {

    /**
     * Save a dismissedTopicUsers.
     *
     * @param dismissedTopicUsersDTO the entity to save.
     * @return the persisted entity.
     */
    DismissedTopicUsersDTO save(DismissedTopicUsersDTO dismissedTopicUsersDTO);

    /**
     * Get all the dismissedTopicUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DismissedTopicUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dismissedTopicUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DismissedTopicUsersDTO> findOne(Long id);

    /**
     * Delete the "id" dismissedTopicUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
