/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.DoNotDisturbTimingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.DoNotDisturbTimings}.
 */
public interface DoNotDisturbTimingsService {

    /**
     * Save a doNotDisturbTimings.
     *
     * @param doNotDisturbTimingsDTO the entity to save.
     * @return the persisted entity.
     */
    DoNotDisturbTimingsDTO save(DoNotDisturbTimingsDTO doNotDisturbTimingsDTO);

    /**
     * Get all the doNotDisturbTimings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoNotDisturbTimingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" doNotDisturbTimings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoNotDisturbTimingsDTO> findOne(Long id);

    /**
     * Delete the "id" doNotDisturbTimings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
