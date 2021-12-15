/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SearchLogsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SearchLogs}.
 */
public interface SearchLogsService {

    /**
     * Save a searchLogs.
     *
     * @param searchLogsDTO the entity to save.
     * @return the persisted entity.
     */
    SearchLogsDTO save(SearchLogsDTO searchLogsDTO);

    /**
     * Get all the searchLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SearchLogsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" searchLogs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SearchLogsDTO> findOne(Long id);

    /**
     * Delete the "id" searchLogs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
