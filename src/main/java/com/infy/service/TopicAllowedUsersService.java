/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicAllowedUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicAllowedUsers}.
 */
public interface TopicAllowedUsersService {

    /**
     * Save a topicAllowedUsers.
     *
     * @param topicAllowedUsersDTO the entity to save.
     * @return the persisted entity.
     */
    TopicAllowedUsersDTO save(TopicAllowedUsersDTO topicAllowedUsersDTO);

    /**
     * Get all the topicAllowedUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicAllowedUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicAllowedUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicAllowedUsersDTO> findOne(Long id);

    /**
     * Delete the "id" topicAllowedUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
