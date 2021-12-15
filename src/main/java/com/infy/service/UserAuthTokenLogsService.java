/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserAuthTokenLogsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserAuthTokenLogs}.
 */
public interface UserAuthTokenLogsService {

    /**
     * Save a userAuthTokenLogs.
     *
     * @param userAuthTokenLogsDTO the entity to save.
     * @return the persisted entity.
     */
    UserAuthTokenLogsDTO save(UserAuthTokenLogsDTO userAuthTokenLogsDTO);

    /**
     * Get all the userAuthTokenLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserAuthTokenLogsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userAuthTokenLogs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserAuthTokenLogsDTO> findOne(Long id);

    /**
     * Delete the "id" userAuthTokenLogs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
