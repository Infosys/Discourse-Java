/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ApiKeysDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ApiKeys}.
 */
public interface ApiKeysService {

    /**
     * Save a apiKeys.
     *
     * @param apiKeysDTO the entity to save.
     * @return the persisted entity.
     */
    ApiKeysDTO save(ApiKeysDTO apiKeysDTO);

    /**
     * Get all the apiKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApiKeysDTO> findAll(Pageable pageable);


    /**
     * Get the "id" apiKeys.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiKeysDTO> findOne(Long id);

    /**
     * Delete the "id" apiKeys.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
