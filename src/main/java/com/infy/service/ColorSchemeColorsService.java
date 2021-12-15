/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ColorSchemeColorsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ColorSchemeColors}.
 */
public interface ColorSchemeColorsService {

    /**
     * Save a colorSchemeColors.
     *
     * @param colorSchemeColorsDTO the entity to save.
     * @return the persisted entity.
     */
    ColorSchemeColorsDTO save(ColorSchemeColorsDTO colorSchemeColorsDTO);

    /**
     * Get all the colorSchemeColors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ColorSchemeColorsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" colorSchemeColors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColorSchemeColorsDTO> findOne(Long id);

    /**
     * Delete the "id" colorSchemeColors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
