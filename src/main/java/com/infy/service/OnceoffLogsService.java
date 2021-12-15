/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.OnceoffLogsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.OnceoffLogs}.
 */
public interface OnceoffLogsService {

    /**
     * Save a onceoffLogs.
     *
     * @param onceoffLogsDTO the entity to save.
     * @return the persisted entity.
     */
    OnceoffLogsDTO save(OnceoffLogsDTO onceoffLogsDTO);

    /**
     * Get all the onceoffLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OnceoffLogsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" onceoffLogs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OnceoffLogsDTO> findOne(Long id);

    /**
     * Delete the "id" onceoffLogs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
