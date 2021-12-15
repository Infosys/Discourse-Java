/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SkippedEmailLogsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SkippedEmailLogs}.
 */
public interface SkippedEmailLogsService {

    /**
     * Save a skippedEmailLogs.
     *
     * @param skippedEmailLogsDTO the entity to save.
     * @return the persisted entity.
     */
    SkippedEmailLogsDTO save(SkippedEmailLogsDTO skippedEmailLogsDTO);

    /**
     * Get all the skippedEmailLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkippedEmailLogsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" skippedEmailLogs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkippedEmailLogsDTO> findOne(Long id);

    /**
     * Delete the "id" skippedEmailLogs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
