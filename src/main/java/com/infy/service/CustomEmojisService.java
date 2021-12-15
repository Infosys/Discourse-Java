/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CustomEmojisDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CustomEmojis}.
 */
public interface CustomEmojisService {

    /**
     * Save a customEmojis.
     *
     * @param customEmojisDTO the entity to save.
     * @return the persisted entity.
     */
    CustomEmojisDTO save(CustomEmojisDTO customEmojisDTO);

    /**
     * Get all the customEmojis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomEmojisDTO> findAll(Pageable pageable);


    /**
     * Get the "id" customEmojis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomEmojisDTO> findOne(Long id);

    /**
     * Delete the "id" customEmojis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
