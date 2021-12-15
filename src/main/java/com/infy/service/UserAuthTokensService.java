/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserAuthTokensDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserAuthTokens}.
 */
public interface UserAuthTokensService {

    /**
     * Save a userAuthTokens.
     *
     * @param userAuthTokensDTO the entity to save.
     * @return the persisted entity.
     */
    UserAuthTokensDTO save(UserAuthTokensDTO userAuthTokensDTO);

    /**
     * Get all the userAuthTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserAuthTokensDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userAuthTokens.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserAuthTokensDTO> findOne(Long id);

    /**
     * Delete the "id" userAuthTokens.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
