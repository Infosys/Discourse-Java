/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ThemeSettingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ThemeSettings}.
 */
public interface ThemeSettingsService {

    /**
     * Save a themeSettings.
     *
     * @param themeSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    ThemeSettingsDTO save(ThemeSettingsDTO themeSettingsDTO);

    /**
     * Get all the themeSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemeSettingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" themeSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemeSettingsDTO> findOne(Long id);

    /**
     * Delete the "id" themeSettings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
