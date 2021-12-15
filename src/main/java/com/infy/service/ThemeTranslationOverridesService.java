/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ThemeTranslationOverridesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ThemeTranslationOverrides}.
 */
public interface ThemeTranslationOverridesService {

    /**
     * Save a themeTranslationOverrides.
     *
     * @param themeTranslationOverridesDTO the entity to save.
     * @return the persisted entity.
     */
    ThemeTranslationOverridesDTO save(ThemeTranslationOverridesDTO themeTranslationOverridesDTO);

    /**
     * Get all the themeTranslationOverrides.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemeTranslationOverridesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" themeTranslationOverrides.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemeTranslationOverridesDTO> findOne(Long id);

    /**
     * Delete the "id" themeTranslationOverrides.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
