/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicUsers}.
 */
public interface TopicUsersService {

    /**
     * Save a topicUsers.
     *
     * @param topicUsersDTO the entity to save.
     * @return the persisted entity.
     */
    TopicUsersDTO save(TopicUsersDTO topicUsersDTO);

    /**
     * Get all the topicUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicUsersDTO> findOne(Long id);

    /**
     * Delete the "id" topicUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
