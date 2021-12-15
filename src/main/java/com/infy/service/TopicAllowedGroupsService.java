/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicAllowedGroupsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicAllowedGroups}.
 */
public interface TopicAllowedGroupsService {

    /**
     * Save a topicAllowedGroups.
     *
     * @param topicAllowedGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    TopicAllowedGroupsDTO save(TopicAllowedGroupsDTO topicAllowedGroupsDTO);

    /**
     * Get all the topicAllowedGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicAllowedGroupsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicAllowedGroups.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicAllowedGroupsDTO> findOne(Long id);

    /**
     * Delete the "id" topicAllowedGroups.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
