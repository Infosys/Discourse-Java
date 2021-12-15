/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserApiKeyScopesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserApiKeyScopes}.
 */
public interface UserApiKeyScopesService {

    /**
     * Save a userApiKeyScopes.
     *
     * @param userApiKeyScopesDTO the entity to save.
     * @return the persisted entity.
     */
    UserApiKeyScopesDTO save(UserApiKeyScopesDTO userApiKeyScopesDTO);

    /**
     * Get all the userApiKeyScopes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserApiKeyScopesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userApiKeyScopes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserApiKeyScopesDTO> findOne(Long id);

    /**
     * Delete the "id" userApiKeyScopes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
