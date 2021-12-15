/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BadgeTypesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.BadgeTypes}.
 */
public interface BadgeTypesService {

    /**
     * Save a badgeTypes.
     *
     * @param badgeTypesDTO the entity to save.
     * @return the persisted entity.
     */
    BadgeTypesDTO save(BadgeTypesDTO badgeTypesDTO);

    /**
     * Get all the badgeTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgeTypesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" badgeTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeTypesDTO> findOne(Long id);

    /**
     * Delete the "id" badgeTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
