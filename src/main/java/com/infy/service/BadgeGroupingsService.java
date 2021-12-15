/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BadgeGroupingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.BadgeGroupings}.
 */
public interface BadgeGroupingsService {

    /**
     * Save a badgeGroupings.
     *
     * @param badgeGroupingsDTO the entity to save.
     * @return the persisted entity.
     */
    BadgeGroupingsDTO save(BadgeGroupingsDTO badgeGroupingsDTO);

    /**
     * Get all the badgeGroupings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgeGroupingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" badgeGroupings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeGroupingsDTO> findOne(Long id);

    /**
     * Delete the "id" badgeGroupings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
