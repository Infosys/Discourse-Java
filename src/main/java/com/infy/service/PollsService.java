/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PollsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Polls}.
 */
public interface PollsService {

    /**
     * Save a polls.
     *
     * @param pollsDTO the entity to save.
     * @return the persisted entity.
     */
    PollsDTO save(PollsDTO pollsDTO);

    /**
     * Get all the polls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PollsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" polls.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PollsDTO> findOne(Long id);

    /**
     * Delete the "id" polls.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
