/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicEmbedsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicEmbeds}.
 */
public interface TopicEmbedsService {

    /**
     * Save a topicEmbeds.
     *
     * @param topicEmbedsDTO the entity to save.
     * @return the persisted entity.
     */
    TopicEmbedsDTO save(TopicEmbedsDTO topicEmbedsDTO);

    /**
     * Get all the topicEmbeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicEmbedsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicEmbeds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicEmbedsDTO> findOne(Long id);

    /**
     * Delete the "id" topicEmbeds.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
