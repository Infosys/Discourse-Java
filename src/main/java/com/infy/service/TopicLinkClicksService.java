/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicLinkClicksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicLinkClicks}.
 */
public interface TopicLinkClicksService {

    /**
     * Save a topicLinkClicks.
     *
     * @param topicLinkClicksDTO the entity to save.
     * @return the persisted entity.
     */
    TopicLinkClicksDTO save(TopicLinkClicksDTO topicLinkClicksDTO);

    /**
     * Get all the topicLinkClicks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicLinkClicksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicLinkClicks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicLinkClicksDTO> findOne(Long id);

    /**
     * Delete the "id" topicLinkClicks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
