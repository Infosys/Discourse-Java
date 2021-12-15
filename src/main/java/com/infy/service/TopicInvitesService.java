/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicInvitesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicInvites}.
 */
public interface TopicInvitesService {

    /**
     * Save a topicInvites.
     *
     * @param topicInvitesDTO the entity to save.
     * @return the persisted entity.
     */
    TopicInvitesDTO save(TopicInvitesDTO topicInvitesDTO);

    /**
     * Get all the topicInvites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicInvitesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicInvites.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicInvitesDTO> findOne(Long id);

    /**
     * Delete the "id" topicInvites.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
