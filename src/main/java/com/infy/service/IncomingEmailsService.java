/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.IncomingEmailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.IncomingEmails}.
 */
public interface IncomingEmailsService {

    /**
     * Save a incomingEmails.
     *
     * @param incomingEmailsDTO the entity to save.
     * @return the persisted entity.
     */
    IncomingEmailsDTO save(IncomingEmailsDTO incomingEmailsDTO);

    /**
     * Get all the incomingEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncomingEmailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" incomingEmails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncomingEmailsDTO> findOne(Long id);

    /**
     * Delete the "id" incomingEmails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
