/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupMentionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupMentions}.
 */
public interface GroupMentionsService {

    /**
     * Save a groupMentions.
     *
     * @param groupMentionsDTO the entity to save.
     * @return the persisted entity.
     */
    GroupMentionsDTO save(GroupMentionsDTO groupMentionsDTO);

    /**
     * Get all the groupMentions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupMentionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupMentions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupMentionsDTO> findOne(Long id);

    /**
     * Delete the "id" groupMentions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
