/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ThemeFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ThemeFields}.
 */
public interface ThemeFieldsService {

    /**
     * Save a themeFields.
     *
     * @param themeFieldsDTO the entity to save.
     * @return the persisted entity.
     */
    ThemeFieldsDTO save(ThemeFieldsDTO themeFieldsDTO);

    /**
     * Get all the themeFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemeFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" themeFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemeFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" themeFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
