/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicTagsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicTags}.
 */
public interface TopicTagsService {

    /**
     * Save a topicTags.
     *
     * @param topicTagsDTO the entity to save.
     * @return the persisted entity.
     */
    TopicTagsDTO save(TopicTagsDTO topicTagsDTO);

    /**
     * Get all the topicTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicTagsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicTags.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicTagsDTO> findOne(Long id);

    /**
     * Delete the "id" topicTags.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
