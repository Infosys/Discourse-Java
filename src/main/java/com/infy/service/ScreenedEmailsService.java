/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ScreenedEmailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ScreenedEmails}.
 */
public interface ScreenedEmailsService {

    /**
     * Save a screenedEmails.
     *
     * @param screenedEmailsDTO the entity to save.
     * @return the persisted entity.
     */
    ScreenedEmailsDTO save(ScreenedEmailsDTO screenedEmailsDTO);

    /**
     * Get all the screenedEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScreenedEmailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" screenedEmails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScreenedEmailsDTO> findOne(Long id);

    /**
     * Delete the "id" screenedEmails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
