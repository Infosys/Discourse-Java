/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.StylesheetCacheDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.StylesheetCache}.
 */
public interface StylesheetCacheService {

    /**
     * Save a stylesheetCache.
     *
     * @param stylesheetCacheDTO the entity to save.
     * @return the persisted entity.
     */
    StylesheetCacheDTO save(StylesheetCacheDTO stylesheetCacheDTO);

    /**
     * Get all the stylesheetCaches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StylesheetCacheDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stylesheetCache.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StylesheetCacheDTO> findOne(Long id);

    /**
     * Delete the "id" stylesheetCache.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
