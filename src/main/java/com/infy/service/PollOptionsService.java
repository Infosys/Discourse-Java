/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PollOptionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PollOptions}.
 */
public interface PollOptionsService {

    /**
     * Save a pollOptions.
     *
     * @param pollOptionsDTO the entity to save.
     * @return the persisted entity.
     */
    PollOptionsDTO save(PollOptionsDTO pollOptionsDTO);

    /**
     * Get all the pollOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PollOptionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pollOptions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PollOptionsDTO> findOne(Long id);

    /**
     * Delete the "id" pollOptions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
