/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SiteSettingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SiteSettings}.
 */
public interface SiteSettingsService {

    /**
     * Save a siteSettings.
     *
     * @param siteSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    SiteSettingsDTO save(SiteSettingsDTO siteSettingsDTO);

    /**
     * Get all the siteSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteSettingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" siteSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SiteSettingsDTO> findOne(Long id);

    /**
     * Delete the "id" siteSettings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
