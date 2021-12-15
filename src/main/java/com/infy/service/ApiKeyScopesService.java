/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ApiKeyScopesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ApiKeyScopes}.
 */
public interface ApiKeyScopesService {

    /**
     * Save a apiKeyScopes.
     *
     * @param apiKeyScopesDTO the entity to save.
     * @return the persisted entity.
     */
    ApiKeyScopesDTO save(ApiKeyScopesDTO apiKeyScopesDTO);

    /**
     * Get all the apiKeyScopes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApiKeyScopesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" apiKeyScopes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiKeyScopesDTO> findOne(Long id);

    /**
     * Delete the "id" apiKeyScopes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
