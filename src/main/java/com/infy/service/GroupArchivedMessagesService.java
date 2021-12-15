/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupArchivedMessagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupArchivedMessages}.
 */
public interface GroupArchivedMessagesService {

    /**
     * Save a groupArchivedMessages.
     *
     * @param groupArchivedMessagesDTO the entity to save.
     * @return the persisted entity.
     */
    GroupArchivedMessagesDTO save(GroupArchivedMessagesDTO groupArchivedMessagesDTO);

    /**
     * Get all the groupArchivedMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupArchivedMessagesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupArchivedMessages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupArchivedMessagesDTO> findOne(Long id);

    /**
     * Delete the "id" groupArchivedMessages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
