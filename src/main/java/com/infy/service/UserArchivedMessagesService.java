/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserArchivedMessagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserArchivedMessages}.
 */
public interface UserArchivedMessagesService {

    /**
     * Save a userArchivedMessages.
     *
     * @param userArchivedMessagesDTO the entity to save.
     * @return the persisted entity.
     */
    UserArchivedMessagesDTO save(UserArchivedMessagesDTO userArchivedMessagesDTO);

    /**
     * Get all the userArchivedMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserArchivedMessagesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userArchivedMessages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserArchivedMessagesDTO> findOne(Long id);

    /**
     * Delete the "id" userArchivedMessages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
