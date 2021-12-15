/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.EmailLogsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.EmailLogs}.
 */
public interface EmailLogsService {

    /**
     * Save a emailLogs.
     *
     * @param emailLogsDTO the entity to save.
     * @return the persisted entity.
     */
    EmailLogsDTO save(EmailLogsDTO emailLogsDTO);

    /**
     * Get all the emailLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailLogsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" emailLogs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailLogsDTO> findOne(Long id);

    /**
     * Delete the "id" emailLogs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
