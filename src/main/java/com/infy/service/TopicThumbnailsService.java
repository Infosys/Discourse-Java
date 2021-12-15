/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicThumbnailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicThumbnails}.
 */
public interface TopicThumbnailsService {

    /**
     * Save a topicThumbnails.
     *
     * @param topicThumbnailsDTO the entity to save.
     * @return the persisted entity.
     */
    TopicThumbnailsDTO save(TopicThumbnailsDTO topicThumbnailsDTO);

    /**
     * Get all the topicThumbnails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicThumbnailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicThumbnails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicThumbnailsDTO> findOne(Long id);

    /**
     * Delete the "id" topicThumbnails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
