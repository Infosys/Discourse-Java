/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicTimersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicTimers}.
 */
public interface TopicTimersService {

    /**
     * Save a topicTimers.
     *
     * @param topicTimersDTO the entity to save.
     * @return the persisted entity.
     */
    TopicTimersDTO save(TopicTimersDTO topicTimersDTO);

    /**
     * Get all the topicTimers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicTimersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicTimers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicTimersDTO> findOne(Long id);

    /**
     * Delete the "id" topicTimers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
