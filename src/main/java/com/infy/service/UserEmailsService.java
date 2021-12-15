/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserEmailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserEmails}.
 */
public interface UserEmailsService {

    /**
     * Save a userEmails.
     *
     * @param userEmailsDTO the entity to save.
     * @return the persisted entity.
     */
    UserEmailsDTO save(UserEmailsDTO userEmailsDTO);

    /**
     * Get all the userEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserEmailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userEmails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserEmailsDTO> findOne(Long id);

    /**
     * Delete the "id" userEmails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
